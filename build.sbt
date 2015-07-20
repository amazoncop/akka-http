name := "spray-intro"

//version := "1.0"

scalaVersion  := "2.11.2"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

//enablePlugins(JavaAppPackaging)

libraryDependencies ++= {
  val akkaV       = "2.3.10"
  val akkaStreamV = "1.0-RC2"
  val scalaTestV  = "2.2.4"
  Seq(
    "com.typesafe.akka" %% "akka-actor"                           % akkaV,
    "com.typesafe.akka" %% "akka-stream-experimental"             % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-core-experimental"          % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-scala-experimental"         % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental"    % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-testkit-scala-experimental" % akkaStreamV,
    "org.scalatest"     %% "scalatest"                            % scalaTestV % "test"
  )
}

//Revolver.settings
