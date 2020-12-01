# smart-apps

华智有为智慧灯杆的智慧应用系统


应用平台后端打包：
预置条件：
1、将物联网IOT的包部署到本地Maven仓库
依赖三个物联网平台的包， service-sdk 其他两个依赖道这里；  iot-device-services  domain-models
要是再eclipse里打包，可以把这三工程编译过去后，这里打包会自动从本地仓库获取
2、smart-apps代码克隆到本地

每次打包步骤：
1、进入common工程（cd common），把公共工程部署到本地Maven仓库（mvn clean install）
2、返回smart-apps根目录（cd ..），执行打包命令（mvn clean package）
3、在各工程的target目录下（cd XXX/target），获得相应的jar包

运行
application.yaml--aly下载的一个正在用的 =  application.yaml
sudo java -XX:NativeMemoryTracking=summary -Xms256m -Xmx512m -XX:-TieredCompilation -Xss256k -XX:+UseG1GC -XX:+UseStringDeduplication -jar /iot/smartapp/token-0.0.1-SNAPSHOT.jar -Dconfig=/iot/smartapp/application.yaml
