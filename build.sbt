
name := "knolx"

version   := 	"1.0"

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
                      		"mysql"                %     "mysql-connector-java"     %      "5.1.21",
                       		"org.scalatest"        %%    "scalatest"    	        %      "2.2.2"     %     "test",
		     				"com.typesafe"         %     "config"                   %      "1.2.1",
	       	           		"ch.qos.logback"       %     "logback-classic"          %      "1.0.13",
							"com.typesafe.slick"   %%    "slick" 					%      "2.1.0",
							"com.typesafe.akka"    %%  	 "akka-actor" 				% 	   "2.3.9"
                   	   )


parallelExecution in Test := false
