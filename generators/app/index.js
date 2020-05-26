"use strict";
const Generator = require("yeoman-generator");
// Const chalk = require("chalk");
// Const yosay = require("yosay");
const mkdirp = require("mkdirp");

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
    var packageDir = this.props.appPackage.split(".").join("/");
    var oldPackageDir = "com/wiredcraft/androidtemplate";
    this.log("dir:" + packageDir);

    var appPath = this.sourceRoot() + "/";
    this.log("appPath: " + appPath);

    // File
    this.fs.copy(appPath + "AndroidTemplate.iml", "AndroidTemplate.iml");
    this.fs.copy(appPath + "meta.gradle", "meta.gradle");
    this.fs.copy(appPath + "gradle.properties", "gradle.properties");
    this.fs.copy(appPath + "settings.gradle", "settings.gradle");
    this.fs.copy(appPath + "local.properties", "local.properties");
    this.fs.copy(appPath + "gradlew.bat", "gradlew.bat");
    this.fs.copy(appPath + "gradlew", "gradlew");

    // Dir
    this.fs.copy(appPath + "security", "security");
    this.fs.copy(appPath + "gradle", "gradle");
    this.fs.copy(appPath + 'app/src/main/res', 'app/src/main/res');

    mkdirp(appPath + "app/src/main/java/" + packageDir);

    this.fs.copyTpl(appPath + 'app/proguard-rules.pro', 'app/proguard-rules.pro', this.props);
    this.fs.copyTpl(appPath + 'README.md', 'README.md', this.props);
    this.fs.copyTpl(appPath + 'build.gradle', 'build.gradle', this.props);
    this.fs.copyTpl(appPath + 'app/build.gradle', 'app/build.gradle', this.props);

    this.fs.copyTpl(appPath + 'app/src/androidTest/java/com/wiredcraft/androidtemplate', 'app/src/androidTest/java/' + packageDir, this.props);
    this.fs.copyTpl(appPath + 'app/src/dev/java/com/wiredcraft/androidtemplate', 'app/src/dev/java/' + packageDir, this.props);
    this.fs.copyTpl(appPath + 'app/src/main/java/com/wiredcraft/androidtemplate', 'app/src/main/java/' + packageDir, this.props);
    this.fs.copyTpl(appPath + 'app/src/prod/java/com/wiredcraft/androidtemplate', 'app/src/prod/java/' + packageDir, this.props);
    this.fs.copyTpl(appPath + 'app/src/stag/java/com/wiredcraft/androidtemplate', 'app/src/stag/java/' + packageDir, this.props);
    this.fs.copyTpl(appPath + 'app/src/test/java/com/wiredcraft/androidtemplate', 'app/src/test/java/' + packageDir, this.props);
    this.fs.copyTpl(appPath + 'app/src/main/res/layout', 'app/src/main/res/layout', this.props);
    this.fs.copyTpl(appPath + 'app/src/main/res/navigation', 'app/src/main/res/navigation', this.props);
    this.fs.copyTpl(appPath + 'app/src/main/AndroidManifest.xml', 'app/src/main/AndroidManifest.xml', this.props);
  }

  install() {
    this.installDependencies();
  }
};
