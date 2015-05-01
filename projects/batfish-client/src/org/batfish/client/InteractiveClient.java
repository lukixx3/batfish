package org.batfish.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import jline.console.ConsoleReader;
import jline.console.completer.Completer;
import jline.console.completer.FileNameCompleter;
import jline.console.completer.StringsCompleter;


public class InteractiveClient {
   
//   public static void usage() {
//      System.out.println("Usage: java " + InteractiveClient.class.getName()
//          + " [none/simple/files/dictionary [trigger mask]]");
//      System.out.println("  none - no completors");
//      System.out.println("  simple - a simple completor that comples "
//          + "\"foo\", \"bar\", and \"baz\"");
//      System.out
//          .println("  files - a completor that comples " + "file names");
//      System.out.println("  classes - a completor that comples "
//          + "java class names");
//      System.out
//          .println("  trigger - a special word which causes it to assume "
//              + "the next line is a password");
//      System.out.println("  mask - is the character to print in place of "
//          + "the actual password character");
//      System.out.println("  color - colored prompt and feedback");
//      System.out.println("\n  E.g - java Example simple su '*'\n"
//          + "will use the simple compleator with 'su' triggering\n"
//          + "the use of '*' as a password mask.");
//  }
//
//   public InteractiveClient(String[] args)  {
//      try {
//          Character mask = null;
//          String trigger = null;
//          boolean color = false;
//
//          ConsoleReader reader = new ConsoleReader();
//
//          reader.setPrompt("prompt> ");
//
//          if ((args == null) || (args.length == 0)) {
//              usage();
//
//              return;
//          }
//
//          List<Completer> completors = new LinkedList<Completer>();
//
//          if (args.length > 0) {
//              if (args[0].equals("none")) {
//              }
//              else if (args[0].equals("files")) {
//                  completors.add(new FileNameCompleter());
//              }
//              else if (args[0].equals("simple")) {
//                  completors.add(new StringsCompleter("foo", "bar", "baz"));
//              }
//              else if (args[0].equals("color")) {
//                  color = true;
//                  reader.setPrompt("\u001B[1mfoo\u001B[0m@bar\u001B[32m@baz\u001B[0m> ");
//              }
//              else {
//                  usage();
//
//                  return;
//              }
//          }
//
//          if (args.length == 3) {
//              mask = args[2].charAt(0);
//              trigger = args[1];
//          }
//
//          for (Completer c : completors) {
//              reader.addCompleter(c);
//          }
//
//          String line;
//          PrintWriter out = new PrintWriter(reader.getOutput());
//
//          while ((line = reader.readLine()) != null) {
//              if (color){
//                  out.println("\u001B[33m======>\u001B[0m\"" + line + "\"");
//
//              } else {
//                  out.println("======>\"" + line + "\"");
//              }
//              out.flush();
//
//              // If we input the special word then we will mask
//              // the next line.
//              if ((trigger != null) && (line.compareTo(trigger) == 0)) {
//                  line = reader.readLine("password> ", mask);
//              }
//              if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
//                  break;
//              }
//              if (line.equalsIgnoreCase("cls")) {
//                  reader.clearScreen();
//              }
//
//              String[] words = line.split("\\s+");
//              
//              if (words.length > 0)
//                 processCommand(line, out);
//          }
//      }
//      catch (Throwable t) {
//          t.printStackTrace();
//      }
//  }

   private BfCoordWorkHelper _workHelper;
   private BfCoordPoolHelper _poolHelper;
   
  public InteractiveClient(String workMgr, String poolMgr)  {
      try {
         
         _workHelper = new BfCoordWorkHelper(workMgr);
         _poolHelper = new BfCoordPoolHelper(poolMgr);

          ConsoleReader reader = new ConsoleReader();
          reader.setPrompt("batfish> ");

          List<Completer> completors = new LinkedList<Completer>();
          completors.add(new StringsCompleter("foo", "bar", "baz"));

          for (Completer c : completors) {
              reader.addCompleter(c);
          }

          String line;
          PrintWriter out = new PrintWriter(reader.getOutput());

          while ((line = reader.readLine()) != null) {
              out.println("======>\"" + line + "\"");
              out.flush();

              if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
                  break;
              }
              if (line.equalsIgnoreCase("cls")) {
                  reader.clearScreen();
              }

              String[] words = line.split("\\s+");
              
              if (words.length > 0) {
                 if (validCommandUsage(words, out))
                    processCommand(words, out);
              }
          }
      }
      catch (Throwable t) {
          t.printStackTrace();
      }
  }
  
   private void processCommand(String[] words, PrintWriter out) {

      try {
         switch (words[0]) {
         case "add-worker":
            boolean result = _poolHelper.addBatfishWorker(words[1]);
            out.println("Result: " + result);
            break;
         default:
            out.println("Unsupported command " + words[0]);
         }
      }
      catch (Exception e) {
         e.printStackTrace();
      }
   }
  
  private boolean validCommandUsage(String[] words, PrintWriter out) {
     return true;
  }
}