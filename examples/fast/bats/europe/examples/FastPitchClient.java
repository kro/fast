/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fast.bats.europe.examples;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.Properties;

import silvertip.CommandLine;
import silvertip.Connection;
import silvertip.Events;
import fast.Message;
import fast.bats.europe.FastPitchMessageParser;
import fast.bats.europe.session.Session;
import fast.soup.SoupTCP2Encoder;

import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class FastPitchClient {
  private static final Logger LOG = Logger.getLogger("FastPitchClient");
  private static final long TIMEOUT_INTERVAL_MSEC = 1000L;
  
  static {
    LOG.setUseParentHandlers(false);
    try {
      LOG.addHandler(new FileHandler("messages.log"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static class CommandLineArgs {
    String hostname, username, password;
    int port;
  }

  public static void main(String[] args) throws IOException {
    new FastPitchClient().run(args);
  }

  private void run(String[] args) throws IOException {
    CommandLineArgs cmdLineArgs = parseCommandLineArgs(args);
    Session session = new Session(new SoupTCP2Encoder());
    Events events = Events.open(TIMEOUT_INTERVAL_MSEC);
    Connection<Message> connection = connection(cmdLineArgs.hostname, cmdLineArgs.port, session);
    CommandLine commandLine = commandLine(cmdLineArgs.username, cmdLineArgs.password, session, connection, events);
    events.register(connection);
    events.register(commandLine);
    events.dispatch();
  }

  private CommandLineArgs parseCommandLineArgs(String[] args) {
    if (args.length < 4) {
      exit("Usage: FastPitchClient HOSTNAME PORT USERNAME PASSWORD");
    }
    CommandLineArgs cmdLineArgs = new CommandLineArgs();
    cmdLineArgs.hostname = args[0];
    try {
        cmdLineArgs.port = Integer.parseInt(args[1]);
    } catch (NumberFormatException e) {
      exit("port must be an integer");
    }
    cmdLineArgs.username = args[2];
    cmdLineArgs.password = args[3];
    return cmdLineArgs;
  }

  private void exit(String reason) {
      System.err.println(reason);
      System.exit(1);
  }

  private CommandLine commandLine(final String username, final String password, final Session session,
      final Connection<Message> connection, final Events events) throws IOException {
    final CommandLine commandLine = CommandLine.open(new CommandLine.Callback() {
      @Override
      public void commandLine(String commandLine) {
        if (commandLine.startsWith("login")) {
          session.login(connection, username, password);
        } else if (commandLine.startsWith("logout")) {
          session.logout(connection);
        } else if (commandLine.startsWith("quit")) {
          events.stop();
        }
      }
    });
    return commandLine;
  }

  private Connection<Message> connection(String hostname, int port, final Session session) throws IOException {
    return Connection.connect(new InetSocketAddress(hostname, port), new FastPitchMessageParser(),
        new Connection.Callback<Message>() {
          public void messages(Connection<Message> connection, Iterator<Message> messages) {
            while (messages.hasNext()) {
              Message message = messages.next();
              session.receive(connection, message);
              LOG.info(message.toString());
            }
          }

          public void idle(Connection<Message> connection) {
            session.heartbeat(connection);
          }

          @Override
          public void closed(Connection<Message> connection) {
          }
        });
  }
}
