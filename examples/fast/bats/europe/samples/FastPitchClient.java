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
package fast.bats.europe.samples;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.Properties;

import silvertip.CommandLine;
import silvertip.Connection;
import silvertip.Events;
import silvertip.Message;
import fast.bats.europe.FastPitchMessage;
import fast.bats.europe.FastPitchMessageParser;
import fast.bats.europe.session.Session;
import fast.soup.SoupTCP2Encoder;

public class FastPitchClient {
  public static void main(String[] args) throws IOException {
    Properties props = new Properties();
    props.load(new FileInputStream("data/FastPitchClient.properties"));
    String hostname = props.getProperty("hostname");
    int port = Integer.parseInt(props.getProperty("port"));
    final String username = props.getProperty("username");
    final String password = props.getProperty("password");
    final Session session = new Session(new SoupTCP2Encoder());

    final Events events = Events.open(30 * 1000);

    final Connection<FastPitchMessage> connection = createConnection(hostname, port);

    final CommandLine commandLine = CommandLine.open(new CommandLine.Callback() {
      @Override
      public void commandLine(String commandLine) {
        print("executing ( " + commandLine + " )");
        if (commandLine.startsWith("login")) {
          session.login(connection, username, password);
        } else
          connection.send(new Message(getMessageBytes(commandLine)));
      }

      private byte[] getMessageBytes(String commandLine) {
        if (commandLine.startsWith("logout")) {
          print("Sending logout request");
          return createLogoutRequest();
        }
        print("Sending client heartbeat");
        return createHeartbeat();
      }

      private byte[] createHeartbeat() {
        return "R\n".getBytes();
      }

      private byte[] createLogoutRequest() {
        return "O\n".getBytes();
      }
    });

    events.register(commandLine);
    events.register(connection);
    events.dispatch();
  }

  private static Connection<FastPitchMessage> createConnection(String hostname, int port) throws IOException {
    return Connection.connect(new InetSocketAddress(hostname, port), new FastPitchMessageParser(),
        new Connection.Callback<FastPitchMessage>() {
          public void messages(Connection<FastPitchMessage> connection, Iterator<FastPitchMessage> messages) {
            while (messages.hasNext()) {
              Message m = messages.next();
              print("Message from server : " + m);
            }
          }

          public void idle(Connection<FastPitchMessage> connection) {
            print("Idle detected.");
          }

          @Override
          public void closed(Connection<FastPitchMessage> connection) {
          }
        });
  }

  private static void print(String str) {
    System.out.println(str);
  }
}
