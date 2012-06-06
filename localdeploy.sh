/Applications/apache-tomcat-6.0.35/bin/shutdown.sh; 
rm -rf /Applications/apache-tomcat-6.0.35/webapps/temula_images/*;
rm -rf /Applications/apache-tomcat-6.0.35/webapps/temula_images.war
mvn clean package;
mv target/temula_images-0.0.1-SNAPSHOT.war target/temula_images.war;
cp target/temula_images.war /Applications/apache-tomcat-6.0.35/webapps/;
/Applications/apache-tomcat-6.0.35/bin/startup.sh;
