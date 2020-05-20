"use strict";
const Generator = require("yeoman-generator");
// Const chalk = require("chalk");
// Const yosay = require("yosay");

module.exports = class extends Generator {
  prompting() {
    // Have Yeoman greet the user.
    // This.log(
    //   Yosay(`Welcome to the sensational ${chalk.red("generator-android-boilerplate")} generator!`)
    // );

    const prompts = [
      {
        name: "name",
        message: "What are you calling your app?",
        store: true,
        default: this.appname // Default to current folder name
      },
      {
        name: "package",
        message: "What package will you be publishing the app under?"
      }
    ];

    return this.prompt(prompts).then(props => {
      this.props = props;
      this.props.appPackage = props.package;
      this.appName = props.name;
      this.appPackage = props.package;
    });
  }

  writing() {
    // var packageDir = this.props.appPackage.replace("/\./g", "/");

    // mkdirp("android");
    // Mkdirp("android/app/src/main/kotlin/" + packageDir);
    // mkdirp("ios");
    // mkdirp("lib");
    // mkdirp("test");

    var appPath = this.sourceRoot() + "/";

    this.fs.copy(appPath + "AndroidTemplate.iml", "AndroidTemplate.iml");
    this.fs.copy(appPath + "build.gradle", "build.gradle");
    this.fs.copy(appPath + "meta.gradle", "meta.gradle");
    this.fs.copy(appPath + "gradle.properties", "gradle.properties");
    this.fs.copy(appPath + "settings.gradle", "settings.gradle");
    this.fs.copy(appPath + "local.properties", "local.properties");
    this.fs.copy(appPath + "gradlew.bat", "gradlew.bat");
    this.fs.copy(appPath + "gradlew", "gradlew");

    this.fs.copy(appPath + "security", "security");
    this.fs.copy(appPath + "gradle", "gradle");

    // mkdirp("android");

    this.fs.copy(appPath + "app", "app");
  }

  install() {
    this.installDependencies();
  }
};
