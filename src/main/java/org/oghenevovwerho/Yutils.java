package org.oghenevovwerho;

import com.maximeroussy.invitrode.WordGenerator;

import javax.lang.model.SourceVersion;
import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import static java.io.File.separator;
import static java.lang.System.*;
import static java.nio.file.Files.createDirectories;
import static java.nio.file.Path.of;

public class Yutils {
  private static final String options = """        
             1 -> create a new project
             2 -> check Yaa version
      """;

  public static void main(String[] args) {
    var intended = "";
    var validOptions = Set.of("1", "2");
    while (true) {
      out.print(options);
      out.print("[INFO] enter one of the options given above: ");
      var intent = System.console().readLine();
      if (!intent.isBlank() && validOptions.contains(intent.strip())) {
        intended = intent;
        break;
      }
    }
    switch (intended.strip()) {
      case "1" -> {
        String projectName = "";
        WordGenerator generator = new WordGenerator();
        var randomNumber = new Random(System.nanoTime());
        var first_word = generator.newWord(randomNumber.nextInt(5, 10)).toLowerCase(Locale.UK);
        var second_word = generator.newWord(randomNumber.nextInt(5, 10)).toLowerCase(Locale.UK);
        var defaultName = first_word + "_" + second_word;
        while (true) {
          out.print("       what would you like to call your project? (" + defaultName + "): ");
          projectName = System.console().readLine();
          if (projectName.isBlank()) {
            projectName = defaultName;
          }
          if (projectName.equals(
              "$".repeat(projectName.length()))) {
            out.println(
                "------ " + projectName + " cannot be used as a project's name"
            );
            continue;
          }
          if (projectName.equals(
              "_".repeat(projectName.length()))) {
            out.println(
                "------ " + projectName + " cannot be used as a project's name"
            );
            continue;
          }
          if (!SourceVersion.isIdentifier(projectName)) {
            out.println(
                "------ only valid Yaa identifiers can be used as project names"
            );
            continue;
          }
          if (SourceVersion.isKeyword(projectName)) {
            out.println(
                "------ Java keywords cannot be used as project names"
            );
            continue;
          }
          var existingInfo = checkIfNameAlreadyExists(projectName);
          if (existingInfo == null) {
            break;
          } else {
            out.println("------ " + projectName + " exists in " + existingInfo.dir
                + " at " + existingInfo.fileIndex);
          }
        }
        String about = projectName + " will change the world for good!";
        out.print("       what is " + projectName + " all about: ");
        var consoleAbout = System.console().readLine();
        if (!consoleAbout.isBlank()) {
          about = consoleAbout;
        }
        try {
          createDirectories(of(getProperty("user.dir") + separator + projectName));

          createDirectories(
              of(getProperty("user.dir") + separator + projectName + separator + "target/classes")
          );

          createDirectories(
              of(getProperty("user.dir") + separator + projectName + separator + "target/classes")
          );

          createDirectories(
              of(getProperty("user.dir") + separator + projectName + separator + "src/test")
          );

          createDirectories(
              of(getProperty("user.dir") + separator + projectName + separator + "src/test/yaa")
          );

          createDirectories(
              of(getProperty("user.dir") + separator + projectName + separator + "src/main/yaa")
          );

          //this one will be the default directory
          createDirectories(
              of(getProperty("user.dir") + separator + projectName + separator + "src/main/yaa/" + projectName)
          );

          createDirectories(
              of(getProperty("user.dir") + separator + projectName + separator + "src/main/resources")
          );

          var pom_file = new File(of(getProperty("user.dir") + separator + projectName
              + separator + "pom.xml").toUri());

          var readme_file = new File(
              of(getProperty("user.dir") + separator + projectName
                  + separator + "README.md").toUri()
          );

          new File(
              of(getProperty("user.dir") + separator + projectName
                  + separator + "LICENSE.txt").toUri()
          );

          Files.writeString(readme_file.toPath(), readmeString(new ReadMeContent(projectName, about)));
          Files.writeString(pom_file.toPath(), pomContent(projectName));

          var main_file = new File(
              of(getProperty("user.dir") + separator + projectName
                  + "/src/main/yaa/" + projectName
                  + "/" + projectName + ".yaa").toUri()
          );

          Files.writeString(
              main_file.toPath(),
              "-> out.println(`Hi {getProperty(`user.name`)}, welcome to " + projectName + "!`);"
          );
          out.println("[INFO] Open " + projectName + "/README.md to know what's next");
        } catch (Exception e) {
          e.printStackTrace();
          out.println("from exception 2");
          exit(0);
        }
      }
      case "2" -> {
        var dateFormatter = new SimpleDateFormat("dd-MMMMM-yyyy", Locale.UK);
        var date = dateFormatter.format(new Date());
        out.println("The Yaa Programming language: " + date);
      }
    }
  }

  private static class CheckReturnInfo {
    public int fileIndex;
    public String dir;

    public CheckReturnInfo(String dir, int fileIndex) {
      this.fileIndex = fileIndex;
      this.dir = dir;
    }
  }

  private static CheckReturnInfo checkIfNameAlreadyExists(String projectName) {
    var working_dir = of(getProperty("user.dir")).toFile();
    var files_in_dir = working_dir.listFiles();
    if (files_in_dir != null) {
      int i = 1;
      for (var file : files_in_dir) {
        if (file.getName().equals(projectName)) {
          return new CheckReturnInfo(working_dir.toString(), i);
        }
        i++;
      }
    }
    return null;
  }

  protected static String pomContent(String projectName) {
    return """
        <?xml version="1.0" encoding="UTF-8"?>
        <project xmlns="http://maven.apache.org/POM/4.0.0"
                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation=
                     "http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
            
        """ +

        "  <modelVersion>4.0.0</modelVersion>\n\n"
        +
        "  <groupId>" + projectName + "</groupId>\n" +
        "  <artifactId>" + projectName + "</artifactId>\n" +
        "  <version>1.0-SNAPSHOT</version>\n" +
        "  <packaging>jar</packaging>\n\n" +

        "  <properties>\n" +
        "    <maven.compiler.source>18</maven.compiler.source>\n" +
        "    <maven.compiler.target>18</maven.compiler.target>\n" +
        "    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>\n" +
        "  </properties>\n\n" +

        "  <build>\n" +
        "    <plugins>\n" +
        "      <plugin>\n" +
        "        <groupId>org.yaa</groupId>\n" +
        "        <artifactId>yaa-maven-plugin</artifactId>\n" +
        "        <version>1.0-SNAPSHOT</version>\n" +
        "        <executions>\n" +
        "          <execution>\n" +
        "            <goals>\n" +
        "              <goal>move-Yaa-resources</goal>\n" +
        "              <goal>compile-Yaa</goal>\n" +
        "            </goals>\n" +
        "          </execution>\n" +
        "        </executions>\n" +
        "      </plugin>\n" +
        "      <plugin>\n" +
        "        <groupId>org.apache.maven.plugins</groupId>\n" +
        "        <artifactId>maven-shade-plugin</artifactId>\n" +
        "        <version>3.4.1</version>\n" +
        "        <executions>\n" +
        "          <execution>\n" +
        "            <phase>package</phase>\n" +
        "              <goals>\n" +
        "                <goal>shade</goal>\n" +
        "              </goals>\n" +
        "              <configuration>\n" +
        "                <transformers>\n" +
        "                  <transformer implementation=\"org.apache.maven.plugins.shade.resource.ManifestResourceTransformer\">\n" +
        "                    <mainClass>" + projectName + "." + projectName + "</mainClass>\n" +
        "                  </transformer>\n" +
        "                </transformers>\n" +
        "              </configuration>\n" +
        "          </execution>\n" +
        "        </executions>\n" +
        "      </plugin>\n" +
        "      <plugin>\n" +
        "        <groupId>org.codehaus.mojo</groupId>\n" +
        "        <artifactId>exec-maven-plugin</artifactId>\n" +
        "        <version>3.0.0</version>\n" +
        "        <configuration>\n" +
        "          <executable>java</executable>\n" +
        "          <arguments>\n" +
        "            <argument>-classpath</argument>\n" +
        "            <!-- automatically creates the classpath using all project dependencies,\n" +
        "                 also adding the project build directory -->\n" +
        "            <classpath/>\n" +
        "            <argument>" + projectName + "." + projectName + "</argument>\n" +
        "          </arguments>\n" +
        "        </configuration>\n" +
        "      </plugin>\n" +
        "      <plugin>" +
        "        <artifactId>maven-surefire-plugin</artifactId>" +
        "        <version>2.22.2</version>" +
        "      </plugin>" +
        "      <plugin>" +
        "      <groupId>org.apache.maven.plugins</groupId>" +
        "        <artifactId>maven-failsafe-plugin</artifactId>" +
        "        <version>3.2.1</version>" +
        "      </plugin>" +
        "    </plugins>\n" +
        "  </build>\n" +
        "</project>";
  }

  private static class ReadMeContent {
    public String projectName;
    public String aboutInfo;

    public ReadMeContent(String projectName, String aboutInfo) {
      this.projectName = projectName;
      this.aboutInfo = aboutInfo;
    }
  }

  public static String readmeString(ReadMeContent readMeContent) {
    var dateFormatter = new SimpleDateFormat("EEEEE, MMMMM dd yyyy. hh:mm:ss a", Locale.UK);
    var date = dateFormatter.format(new Date());
    return "# Welcome to Yaa!\n" +
        "#### " + readMeContent.projectName + ": " + date + "\n\n" +
        "### About\n" +
        readMeContent.aboutInfo + "\n\n" +
        "### Getting started\n\n" +
        "Make sure you have Maven installed\n" +
        "- [Maven download and setup](https://maven.apache.org/download/cgi)\n\n" +
        "Make sure you have Java installed\n" +
        "- [Java download and setup](https://oracle.com/ng/java/technologies/downloads/)\n\n\n" +
        "Go into your project directory\n\n" +
        "```sh\n" +
        "cd " + readMeContent.projectName + "\n" +
        "```\n\n" +
        "#### To compile your code\n\n" +
        "Invoke the maven package command\n\n" +
        "```sh\n" +
        "mvn package\n" +
        "```\n\n" +
        "#### To run the compiled " + readMeContent.projectName + " code\n\n" +
        "Change into the 'target' directory of you project\n" +
        "```sh\n" +
        "cd target\n" +
        "```\n\n" +
        "You will find the compiled jar there\n" +
        "Execute it with the Java 'jar' command\n\n" +
        "```sh\n" +
        "java -jar " + readMeContent.projectName + "-1.0-SNAPSHOT.jar\n" +
        "```\n\n\n" +
        "### Note: the jar you chose should not have a '-shaded' in its name\n\n" +

        "Another way of executing your code is by making use of the mvn exec plugin\n" +
        "```sh\n" +
        "mvn compile exec:exec\n" +
        "```\n\n\n" +
        "This will compile your code and run it immediately with no break in between\n\n" +

        "### Example \n" +
        "For your newly created project '" + readMeContent.projectName + "'\n" +
        "You start by going into the project directory\n\n" +
        "```sh\n" +
        "cd " + readMeContent.projectName + "\n" +
        "```\n\n" +
        "This will change into your project (" + readMeContent.projectName + ") directory\n\n" +
        "You have to compile " + readMeContent.projectName + " in order to run it\n\n" +
        "```sh\n" +
        "mvn package\n" +
        "```\n\n" +
        "After compilation, change into the target directory of your project\n\n" +
        "```sh\n" +
        "cd target\n" +
        "```\n\n" +
        "You will find different jar files there\n\n" +
        "Assuming you compiled against the generated build file without editing\n\n" +
        "You should see a jar named " + readMeContent.projectName + "-1.0-SNAPSHOT.jar\n\n" +
        "To run this jar\n\n" +
        "```sh\n" +
        "java -jar " + readMeContent.projectName + "-1.0-SNAPSHOT.jar\n" +
        "```\n\n" +

        "You could also run your code by making use of the mvn exec plugin\n" +
        "```sh\n" +
        "mvn compile exec:exec\n" +
        "```\n\n\n" +
        "This will compile your code and run it immediately with no break in between\n\n" +

        "### Note: Yaa uses Maven as its build and dependency management system (for now)\n" +
        "- [Maven Documentation](https://maven.apache.org)\n\n\n" +
        "# Enjoy!!!\n";
  }
}