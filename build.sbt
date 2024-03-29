organization  := "com.example"

version       := "0.1"

scalaVersion  := "2.11.6"


scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV = "2.3.9"
  val sprayV = "1.3.3"
  Seq(
    "io.spray"            %%  "spray-can"     % sprayV,
    "io.spray"            %%  "spray-routing" % sprayV,
    "io.spray"            %%  "spray-testkit" % sprayV  % "test",
    "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"  % akkaV   % "test",
    "org.specs2"          %%  "specs2-core"   % "2.3.11" % "test",
    "org.scalaj" %% "scalaj-http" % "2.4.2",
    "io.circe" %% "circe-core" % "0.11.2",
    "io.circe" %% "circe-generic" % "0.11.2",
    "io.circe" %% "circe-parser" % "0.11.2"
  )
}