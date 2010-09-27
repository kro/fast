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

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

import silvertip.CommandLine;
import silvertip.Connection;
import silvertip.Events;

import fast.bats.europe.examples.commands.*;
import fast.bats.europe.session.Session;

public class FastPitchClient {
  private static final long TIMEOUT_INTERVAL_MSEC = 1000L;

  private final Map<String, Command> commands = new HashMap<String, Command>();
  private Session session;
  private Connection<?> connection;
  private Events events;

  public static void main(String[] args) throws IOException {
    new FastPitchClient().run(args);
  }

  public FastPitchClient() {
    registerCommands();
  }

  private void run(String[] args) throws IOException {
    events = Events.open(TIMEOUT_INTERVAL_MSEC);
    CommandLine commandLine = commandLine();
    events.register(commandLine);
    events.dispatch();
  }

  private void exit(String reason) {
      System.err.println(reason);
      System.exit(1);
  }

  private CommandLine commandLine() throws IOException {
    return CommandLine.open(new CommandLine.Callback() {
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
