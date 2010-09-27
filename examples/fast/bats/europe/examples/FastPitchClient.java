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
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

import silvertip.CommandLine;
import silvertip.Connection;
import silvertip.Events;
import fast.Message;
import fast.bats.europe.FastPitchMessageParser;
import fast.bats.europe.session.Session;
import fast.soup.SoupTCP2Encoder;

import java.util.logging.FileHandler;
import java.util.logging.Logger;

import fast.bats.europe.examples.commands.*;

public class FastPitchClient {
  private static final Logger LOG = Logger.getLogger("FastPitchClient");
  private static final long TIMEOUT_INTERVAL_MSEC = 1000L;

  private final Map<String, Command> commands = new HashMap<String, Command>();
  private Session session;
  private Connection<?> connection;
  private Events events;

  static {
    LOG.setUseParentHandlers(false);
    try {
      LOG.addHandler(new FileHandler("messages.log"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) throws IOException {
    new FastPitchClient().run(args);
  }

  public FastPitchClient() {
    registerCommands();
  }

  private void run(String[] args) throws IOException {
    Session session = new Session(new SoupTCP2Encoder());
    events = Events.open(TIMEOUT_INTERVAL_MSEC);
    Connection<Message> connection = null; 
    CommandLine commandLine = commandLine(null, null, session, connection, events);
    events.register(commandLine);
    events.dispatch();
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
        Scanner scanner = new Scanner(commandLine);
        Command cmd = commands.get(scanner.next().toLowerCase());
        if (cmd == null) {
          System.out.println("unknown command");
        } else {
          try {
            cmd.execute(FastPitchClient.this, scanner);
          } catch (CommandArgException e) {
            System.out.println(e.getMessage());
          }
        }
      }
    });
    return commandLine;
  }

  public void quit() {
    events.stop();
  }

  public Session getSession() {
    return session;
  }

  public void setSession(Session session) {
    this.session = session;
  }

  public Connection<?> getConnection() {
    return connection;
  }

  public void setConnection(Connection<?> connection) throws IOException {
    this.connection = connection;
    events.register(connection);
  }

  private void registerCommands() {
    commands.put("login", new Login());
    commands.put("logout", new Logout());
    commands.put("quit", new Quit());
  }
}
