lazy val root = project
  .in(file("."))
  .settings(Settings.common ++ Settings.noPublish)
  .aggregate(coreJVM, coreJS)

lazy val core = crossProject
  .in(file("."))
  .settings(Settings.common)
  .settings(
    description := "Table like String formatting for pretty command line printing",
    libraryDependencies ++=
      "com.github.dwickern" %% "scala-nameof" % "1.0.3" % "provided" ::
        "org.scalatest" %%% "scalatest" % "3.0.4" % "test" ::
        Nil,
    name := "Tabsi",
    startYear := Some(2017)
  )

lazy val coreJVM = core.jvm

lazy val coreJS = core.js

lazy val documentation = project
  .enablePlugins(BuildInfoPlugin, MicrositesPlugin)
  .settings(Settings.common ++ Settings.noPublish)
  .settings(
    micrositeAnalyticsToken := "UA-64109905-2",
    micrositeAuthor := "Niklas Klein",
    micrositeBaseUrl := s"/${githubProject.value}",
    micrositeCssDirectory := sourceDirectory.value / "stylesheet",
    micrositeDescription := (description in coreJVM).value,
    micrositeDocumentationUrl := {
      val o = organization.value
      val n = (normalizedName in coreJVM).value
      val a = s"${n}_${scalaBinaryVersion.value}"
      val v = version.value
      val p = s"$o.$n".split("\\.").mkString("/")
      s"https://static.javadoc.io/$o/$a/$v/$p/index.html"
    },
    micrositeGithubOwner := "Taig",
    micrositeGithubRepo := githubProject.value,
    micrositeGithubToken := Option(System.getenv("GITHUB_TOKEN")),
    micrositeGitterChannel := false,
    micrositeHighlightTheme := "atom-one-dark",
    micrositeName := (name in coreJVM).value,
    micrositePalette := Map(
      "brand-primary" -> "#3e4959",
      "brand-secondary" -> "#3e4959",
      "brand-tertiary" -> "#3e4959",
      "gray-dark" -> "#3e4959",
      "gray" -> "#837f84",
      "gray-light" -> "#e3e2e3",
      "gray-lighter" -> "#f4f3f4",
      "white-color" -> "#f3f3f3"
    ),
    micrositeTwitterCreator := "@tttaig",
    scalacOptions in Tut -= "-Ywarn-unused-import",
    tutSourceDirectory := sourceDirectory.value
  )
  .settings(
    buildInfoObject := "Build",
    buildInfoPackage := s"${organization.value}.${(normalizedName in coreJVM).value}",
    buildInfoKeys := Seq[BuildInfoKey](
      normalizedName in coreJVM,
      organization,
      version
    )
  )
  .dependsOn(coreJVM)

addCommandAlias("scalafmtAll", ";scalafmt;sbt:scalafmt;test:scalafmt")
