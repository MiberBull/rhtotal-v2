pipeline {
    agent any
    /*agent {
        label 'RhTotal'
    }*/
    environment {
        SONAR_TOKEN = credentials('sonarqube-token')
    }
    tools {
        maven 'M3'
        jdk 'jdk21'
    }
    stages {
        stage('Build Components') {
            failFast true
            parallel {
                stage('Build Eureka') {
                    steps {
                        echo 'Building Eureka Service Spring boot'
                        dir ('eureka-service/'){
                            sh 'mvn clean package'
                        }
                    }
                }
                stage('Build Gateway') {
                    steps {
                        echo 'Building Webservice Gateway Spring boot'
                        dir ('gateway-service/'){
                            sh 'mvn clean package'
                        }
                    }
                }
                stage('Build Security') {
                    steps {
                        echo 'Building Webservice Security Spring boot'
                        dir ('security-service/'){
                            sh 'mvn clean package'
                        }
                    }
                }
                stage('Build Application') {
                    steps {
                        echo 'Building Webservice Application Spring boot'
                        dir ('application-service/'){
                            sh 'mvn clean package'
                        }
                    }
                }
                stage('Build User') {
                    steps {
                        echo 'Building Webservice Spring boot'
                        dir ('user-service/'){
                            sh 'mvn clean package'
                        }
                    }
                }
                stage('Build Onboarding') {
                    steps {
                        echo 'Building Onboarding Service Spring boot'
                        dir ('onboarding-service/'){
                            sh 'mvn clean package'
                        }
                    }
                }
                stage('Build Attendance') {
                    steps {
                        echo 'Building Attendance Service Spring boot'
                        dir ('attendance-service/'){
                            sh 'mvn clean package'
                        }
                    }
                }
                stage('Build HR') {
                    steps {
                        echo 'Building HR Service Spring boot'
                        dir ('hr-service/'){
                            sh 'mvn clean package'
                        }
                    }
                }
                stage('Build Document') {
                    steps {
                        echo 'Building Document Service Spring boot'
                        dir ('document-service/'){
                            sh 'mvn clean package'
                        }
                    }
                }
                stage('Build Frontend Web') {
                    steps {
                        echo 'Building Frontend Angular'
                        dir ('frontend-web/'){
                            sh 'npm install'
                            sh 'npm install three-steps-selector --registry http://devtools.axity.com/nexus3/repository/npm-group/ --save'
                            sh 'npm run build'
                        }
                    }
                }
                /*stage('Build Frontend Mobile') {
                    steps {
                        echo 'Building Frontend Angular'
                        dir ('frontend-mobile/'){
                            sh 'npm install'
                            sh 'npm run build'
                        }
                    }
                }*/
            }
        }
        stage('Sonar Analisis') {
            failFast true
            parallel {
                stage('Sonar Security') {
                    tools {
                        jdk "jdk21"
                    }
                    steps {
                        dir ('security-service/'){
                            sh 'mvn sonar:sonar \
                                -Dsonar.projectKey=18_WorkPoint_RHTotal_Security \
                                -Dsonar.projectName=18_WorkPoint_RHTotal_Security \
                                -Dsonar.sources=src/main \
                                -Dsonar.tests=src/test \
                                -Dsonar.coverage.exclusions=**/*TO.java,**/*DO.java \
                                -Dsonar.host.url=http://devtools.axity.com/sonar7 \
                                -Dsonar.login=${SONAR_TOKEN}'
                        }
                    }
                }
                stage('Sonar Application') {
                    tools {
                        jdk "jdk21"
                    }
                    steps {
                        dir ('application-service/'){
                            sh 'mvn sonar:sonar \
                                -Dsonar.projectKey=18_WorkPoint_RHTotal_Application \
                                -Dsonar.projectName=18_WorkPoint_RHTotal_Application \
                                -Dsonar.sources=src/main \
                                -Dsonar.tests=src/test \
                                -Dsonar.coverage.exclusions=**/*TO.java,**/*DO.java \
                                -Dsonar.host.url=http://devtools.axity.com/sonar7 \
                                -Dsonar.login=${SONAR_TOKEN}'
                        }
                    }
                }
                stage('Sonar User') {
                    tools {
                        jdk "jdk21"
                    }
                    steps {
                        dir ('user-service/'){
                            sh 'mvn sonar:sonar \
                                -Dsonar.projectKey=18_WorkPoint_RHTotal_User \
                                -Dsonar.projectName=18_WorkPoint_RHTotal_User \
                                -Dsonar.sources=src/main \
                                -Dsonar.tests=src/test \
                                -Dsonar.coverage.exclusions=**/*TO.java,**/*DO.java \
                                -Dsonar.host.url=http://devtools.axity.com/sonar7 \
                                -Dsonar.login=${SONAR_TOKEN}'
                        }
                    }
                }
                stage('Sonar Onboarding') {
                    tools {
                        jdk "jdk21"
                    }
                    steps {
                        dir ('onboarding-service/'){
                            sh 'mvn sonar:sonar \
                                -Dsonar.projectKey=18_WorkPoint_DCH_Onboarding \
                                -Dsonar.projectName=18_WorkPoint_DCH_Onboarding \
                                -Dsonar.sources=src/main \
                                -Dsonar.tests=src/test \
                                -Dsonar.coverage.exclusions=**/*TO.java,**/*DO.java \
                                -Dsonar.host.url=http://devtools.axity.com/sonar7 \
                                -Dsonar.login=${SONAR_TOKEN}'
                        }
                    }
                }
                stage('Sonar Attendance') {
                    tools {
                        jdk "jdk21"
                    }
                    steps {
                        dir ('attendance-service/'){
                            sh 'mvn sonar:sonar \
                                -Dsonar.projectKey=18_WorkPoint_DCH_Attendance \
                                -Dsonar.projectName=18_WorkPoint_DCH_Attendance \
                                -Dsonar.sources=src/main \
                                -Dsonar.tests=src/test \
                                -Dsonar.coverage.exclusions=**/*TO.java,**/*DO.java \
                                -Dsonar.host.url=http://devtools.axity.com/sonar7 \
                                -Dsonar.login=${SONAR_TOKEN}'
                        }
                    }
                }
                stage('Sonar HR') {
                    tools {
                        jdk "jdk21"
                    }
                    steps {
                        dir ('hr-service/'){
                            sh 'mvn sonar:sonar \
                                -Dsonar.projectKey=18_WorkPoint_DCH_HR \
                                -Dsonar.projectName=18_WorkPoint_DCH_HR \
                                -Dsonar.sources=src/main \
                                -Dsonar.tests=src/test \
                                -Dsonar.coverage.exclusions=**/*TO.java,**/*DO.java \
                                -Dsonar.host.url=http://devtools.axity.com/sonar7 \
                                -Dsonar.login=${SONAR_TOKEN}'
                        }
                    }
                }
                stage('Sonar Document') {
                    tools {
                        jdk "jdk21"
                    }
                    steps {
                        dir ('document-service/'){
                            sh 'mvn sonar:sonar \
                                -Dsonar.projectKey=18_WorkPoint_DCH_Document \
                                -Dsonar.projectName=18_WorkPoint_DCH_Document \
                                -Dsonar.sources=src/main \
                                -Dsonar.tests=src/test \
                                -Dsonar.coverage.exclusions=**/*TO.java,**/*DO.java \
                                -Dsonar.host.url=http://devtools.axity.com/sonar7 \
                                -Dsonar.login=${SONAR_TOKEN}'
                        }
                    }
                }
                stage('Sonar Frontend Web') {
                    steps {
                        echo 'Building Frontend Angular'
                        dir ('frontend-web/'){
                            sh 'mvn sonar:sonar \
                                -Dsonar.projectKey=18_WorkPoint_RHTotal_Web \
                                -Dsonar.projectName=18_WorkPoint_RHTotal_Web \
                                -Dsonar.sources=src \
                                -Dsonar.coverage.exclusions=**/*TO.java,**/*DO.java \
                                -Dsonar.host.url=http://devtools.axity.com/sonar7 \
                                -Dsonar.login=${SONAR_TOKEN}'
                        }
                    }
                }
                /*stage('Sonar Frontend Mobile') {
                    steps {
                        echo 'Building Frontend Angular'
                        dir ('frontend-mobile/'){
                            sh 'mvn sonar:sonar \
                                -Dsonar.projectKey=18_WorkPoint_RHTotal_Mobile \
                                -Dsonar.projectName=18_WorkPoint_RHTotal_Mobile \
                                -Dsonar.sources=src \
                                -Dsonar.host.url=http://devtools.axity.com/sonar7 \
                                -Dsonar.login=${SONAR_TOKEN}'
                        }
                    }
                }*/
            }
        }
        stage('Docker up') {
            steps {
                echo 'Running on Docker'
                sh 'docker network disconnect rhtotal_rhtotalnet postgres'
                sh 'docker-compose down --rmi all'
                sh 'docker-compose up -d'
                sh 'docker network connect rhtotal_rhtotalnet postgres'
            }
        }
        stage('Liquibase') {
            steps {
                sleep 10
                echo 'Building Webservice Spring boot'
                dir ('database/liquibase/'){
                    sh '$LIQUIBASE_PATH/liquibase --changeLogFile="changesets/db.changelog-master-dev.xml" update'
                }
            }
        }
    }
    post {
        always {
            deleteDir()
        }
        success {
            echo 'I succeeeded!'
        }
        unstable {
            sh 'docker-compose down'
            echo 'I am unstable :/'
        }
        failure {
            sh 'docker-compose down'
            echo 'I failed :('
        }
        changed {
            echo 'Things were different before...'
        }
    }
}
