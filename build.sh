cd $(dirname $0)
mvn -T 2C clean package

rm -rf hacker-cup2020-jar-with-dependencies.jar
cp target/hacker-cup2020-jar-with-dependencies.jar hacker-cup2020-jar-with-dependencies.jar
