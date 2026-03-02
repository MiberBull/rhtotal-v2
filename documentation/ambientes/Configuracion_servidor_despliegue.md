# Generando el ambiente con Jenkins

En este documento de describe el proceso para aprovisionar el ambiente para ejecutar y probar la aplicacion.

## Requisitos

* [Crear un usuario distinto de root](https://www.digitalocean.com/community/tutorials/how-to-add-and-delete-users-on-a-centos-7-server)
* [Instalar Java 8](http://openjdk.java.net/install/)
* [Instalar Jenkins](https://wiki.jenkins.io/display/JENKINS/Installing+Jenkins+on+Red+Hat+distributions)
* [Instalar Liquibase](http://download.liquibase.org/download/)
* [Instalar Docker CE Centos](https://docs.docker.com/install/linux/docker-ce/centos/)
* [Instalar NodeJs v8](https://www.hugeserver.com/kb/install-nodejs8-centos7-debian8-ubuntu16/)
* [Instalar Git](https://www.digitalocean.com/community/tutorials/how-to-install-git-on-centos-7)

## Procedimiento

1. Crear un usuario distinto de root (reemplazar username)  
`$ sudo adduser username`  
`$ passwd username`  
`$ sudo gpasswd -a username wheel`  
NOTA: Hacer logout y despues login con el nuevo usuario, para que los permisos sean evaluados nuevamente.

1. Instalar Java 8  
`$ sudo yum update`  
`$ sudo yum install java-1.8.0-openjdk`

1. Instalar java 8 development  
`$ sudo yum install java-1.8.0-openjdk-devel`

3. Instalar Jenkins  
`$ sudo wget -O /etc/yum.repos.d/jenkins.repo http://pkg.jenkins-ci.org/redhat-stable/jenkins.repo`
`$ sudo rpm --import https://jenkins-ci.org/redhat/jenkins-ci.org.key`
`$ sudo yum install jenkins`

4. Jenkins como servicio  
`$ sudo service jenkins start`
`$ sudo chkconfig jenkins on`  
Opciones para manipular Jenkins: sudo service jenkins start/stop/restart

5. Abrir puertos jenkins  
`$ sudo firewall-cmd --permanent --new-service=jenkins`  
`$ sudo firewall-cmd --permanent --service=jenkins --set-short="Jenkins Service Ports"`  
`$ sudo firewall-cmd --permanent --service=jenkins --set-description="Jenkins service firewalld port exceptions"`  
`$ sudo firewall-cmd --permanent --service=jenkins --add-port=8080/tcp`  
`$ sudo firewall-cmd --permanent --add-service=jenkins`  
`$ sudo firewall-cmd --zone=public --add-service=http --permanent`  
`$ sudo firewall-cmd --reload`  

6. Insalar liquibase  
`cd /usr/local/lib`  
`sudo wget https://github.com/liquibase/liquibase/releases/download/liquibase-parent-3.5.5/liquibase-3.5.5-bin.tar.gz`  
`sudo mkdir liquibase`  
`sudo tar -xzf liquibase-3.5.5-bin.tar.gz -C liquibase`

7. Set Liquibase Enviroment variables y agregar al PATH  
`$ sudo vim /etc/profile.d/liquibase-env.sh`  
`export LIQUIBASE_HOME="/usr/local/lib/liquibase"`  
`$ sudo -s source /etc/profile.d/liquibase-env.sh`  
`$ vi ~/.bash_profile`  
`export PATH=$PATH:/usr/local/lib/liquibase`  
`$ source ~/.bash_profile`  

8. Verificar instalación de liquibase
`$ liquibase --version`

9. Instalar Docker CE  
`$ sudo yum install -y yum-utils device-mapper-persistent-data lvm2`  
`$ sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo`  
`$ sudo yum install docker-ce`  

10. Iniciar y verificar instalacion docker  
`$ sudo systemctl start docker`  
`$ sudo docker run hello-world`  

11. Correr docker sin sudo  
`$ sudo groupadd docker`  
`$ sudo usermod -aG docker $USER`  
NOTA: Hacer logout y despues login, para que los permisos sean evaluados nuevamente.
`$ sudo gpasswd -a jenkins docker`
`$ systemctl restart docker`
`$ sudo service jenkins restart`

12. Instalar docker-compose  
`$ sudo yum install epel-release`  
`$ sudo yum install -y python-pip`  
`$ sudo pip install docker-compose`  
`$ sudo yum upgrade python*`  
`$ docker-compose`  

En caso de presentarse el error: cannot import name UnrewindableBodyError, ejecutar:  

`$ sudo pip uninstall requests`  
`$ sudo pip uninstall urllib3`  
`$ sudo yum remove python-urllib3`  
`$ sudo yum remove python-requests`  
`$ rpm -qa | grep requests`  
`$ pip freeze | grep requests`  
`$ sudo yum install python-urllib3`  
`$ sudo yum install python-requests`  

13. Instalar NodeJs v8  
`$ sudo curl -sL https://rpm.nodesource.com/setup_8.x | bash -`  
`$ sudo rpm -i --nosignature --force '/tmp/tmp.*'` --> Si se presenta un error correr esta linea como sudo, reemplazar el * por el id que retorna el comando en la consola  
`$ sudo yum install nodejs`   

14. Verificar instalacion Nodejs  
`node --version` --> v8.\*.*  
`npm --version` --> 5.\*.*  

15. Instalar Git  
`sudo yum install git`
`git --version`

## Configurar Jenkins

Pasos para la configuraciÃ³n de Jenkins en el servidor de despliegue:

1. Ingresar a la Url de jenkins http://{ip servidor}:8080

2. Aparecera la siguiente pantalla si es la primera vez que se inicia el sistema.

3. Copiar el contenido del archivo que nos indica la pantalla y colocarlo en el campo Administrator password  
`cat /var/lib/jenkins/secrets/initialAdminPassword`

4. Instalar los plugins sugeridos

5. Llenar los campos de un usuario administrador

6. Comenzar a utilizar Jenkins

7. Ingresar Administrar Jenkins / Configurar el sistema

8. Agregar las siguientes variables de entorno

9. Instalar el plugin de Bitbucket




## Server
ServerDesarrollo
ip: 192.168.0.65
usr: root
pwd: COS70r00t
Jenkins
usr: admin
pwd: admin

ServerQA
ip: 192.168.0.72
usr: rhtotal
pwd: admin
Jenkins
usr: admin
pwd: admin