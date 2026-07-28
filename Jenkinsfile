pipeline {
    agent any
    /*agent {
        label 'RhTotal'
    }*/
    triggers {
        pollSCM('H/5 * * * *')
    }
    environment {
        SONAR_TOKEN = credentials('sonarqube-token')
        AES_SECRET_KEY = credentials('aes-secret-key')
    }
    tools {
        maven 'M3'
        jdk 'jdk21'
    }
    stages {
        stage('Build Components') {
            when { branch 'develop' }
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
                        echo 'Building Frontend Web Angular 6 (Node 12)'
                        dir ('frontend-web/src/environments/'){
                            sh 'sed -i "s/REPLACE_WITH_AES_SECRET/${AES_SECRET_KEY}/g" environment.ts'
                            sh 'sed -i "s/REPLACE_WITH_AES_SECRET/${AES_SECRET_KEY}/g" environment.prod.ts'
                        }
                        dir ('frontend-web/'){
                            sh '. ~/.nvm/nvm.sh && nvm use 12 && npm install && npm run build -- --configuration=production'
                        }
                    }
                }
                stage('Build Frontend Mobile') {
                    steps {
                        echo 'Building Frontend Mobile Ionic 3 (Node 12)'
                        dir ('frontend-mobile/src/environments/'){
                            sh 'sed -i "s/REPLACE_WITH_AES_SECRET/${AES_SECRET_KEY}/g" environments.ts'
                        }
                        dir ('frontend-mobile/'){
                            sh '. ~/.nvm/nvm.sh && nvm use 12 && npm rebuild node-sass && npx ionic build --prod'
                        }
                    }
                }
                stage('Build Web Admin') {
                    steps {
                        echo 'Building Web Admin Angular 19 (Node 22)'
                        dir ('apps/web-admin/src/environments/'){
                            sh 'sed -i "s/REPLACE_WITH_AES_SECRET/${AES_SECRET_KEY}/g" environment.ts'
                            sh 'sed -i "s/REPLACE_WITH_AES_SECRET/${AES_SECRET_KEY}/g" environment.prod.ts'
                        }
                        dir ('apps/web-admin/'){
                            sh 'npm install'
                            sh 'npm run build'
                        }
                    }
                }
            }
        }
        stage('Sonar Analisis') {
            when { branch 'develop' }
            failFast true
            parallel {
                stage('Sonar Security') {
                    tools {
                        jdk "jdk21"
                    }
                    steps {
                        dir ('security-service/'){
                            sh 'mvn sonar:sonar \
                                -Dsonar.projectKey=DCH_Total_Security \
                                -Dsonar.projectName=DCH_Total_Security \
                                -Dsonar.sources=src/main \
                                -Dsonar.tests=src/test \
                                -Dsonar.coverage.exclusions=**/*TO.java,**/*DO.java \
                                -Dsonar.host.url=${SONAR_HOST_URL} \
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
                                -Dsonar.projectKey=DCH_Total_Application \
                                -Dsonar.projectName=DCH_Total_Application \
                                -Dsonar.sources=src/main \
                                -Dsonar.tests=src/test \
                                -Dsonar.coverage.exclusions=**/*TO.java,**/*DO.java \
                                -Dsonar.host.url=${SONAR_HOST_URL} \
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
                                -Dsonar.projectKey=DCH_Total_User \
                                -Dsonar.projectName=DCH_Total_User \
                                -Dsonar.sources=src/main \
                                -Dsonar.tests=src/test \
                                -Dsonar.coverage.exclusions=**/*TO.java,**/*DO.java \
                                -Dsonar.host.url=${SONAR_HOST_URL} \
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
                                -Dsonar.projectKey=DCH_Total_Onboarding \
                                -Dsonar.projectName=DCH_Total_Onboarding \
                                -Dsonar.sources=src/main \
                                -Dsonar.tests=src/test \
                                -Dsonar.coverage.exclusions=**/*TO.java,**/*DO.java \
                                -Dsonar.host.url=${SONAR_HOST_URL} \
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
                                -Dsonar.projectKey=DCH_Total_Attendance \
                                -Dsonar.projectName=DCH_Total_Attendance \
                                -Dsonar.sources=src/main \
                                -Dsonar.tests=src/test \
                                -Dsonar.coverage.exclusions=**/*TO.java,**/*DO.java \
                                -Dsonar.host.url=${SONAR_HOST_URL} \
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
                                -Dsonar.projectKey=DCH_Total_HR \
                                -Dsonar.projectName=DCH_Total_HR \
                                -Dsonar.sources=src/main \
                                -Dsonar.tests=src/test \
                                -Dsonar.coverage.exclusions=**/*TO.java,**/*DO.java \
                                -Dsonar.host.url=${SONAR_HOST_URL} \
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
                                -Dsonar.projectKey=DCH_Total_Document \
                                -Dsonar.projectName=DCH_Total_Document \
                                -Dsonar.sources=src/main \
                                -Dsonar.tests=src/test \
                                -Dsonar.coverage.exclusions=**/*TO.java,**/*DO.java \
                                -Dsonar.host.url=${SONAR_HOST_URL} \
                                -Dsonar.login=${SONAR_TOKEN}'
                        }
                    }
                }
                stage('Sonar Frontend Web') {
                    steps {
                        echo 'Analyzing Frontend Web with SonarQube Scanner'
                        dir ('frontend-web/'){
                            sh 'npx sonar-scanner \
                                -Dsonar.projectKey=DCH_Total_Web \
                                -Dsonar.projectName=DCH_Total_Web \
                                -Dsonar.sources=src \
                                -Dsonar.exclusions=**/node_modules/**,**/*.spec.ts \
                                -Dsonar.host.url=${SONAR_HOST_URL} \
                                -Dsonar.login=${SONAR_TOKEN}'
                        }
                    }
                }
                stage('Sonar Web Admin') {
                    steps {
                        echo 'Analyzing Web Admin with SonarQube Scanner'
                        dir ('apps/web-admin/'){
                            sh 'npx sonar-scanner \
                                -Dsonar.projectKey=DCH_Total_WebAdmin \
                                -Dsonar.projectName=DCH_Total_WebAdmin \
                                -Dsonar.sources=src \
                                -Dsonar.exclusions=**/node_modules/**,**/*.spec.ts \
                                -Dsonar.host.url=${SONAR_HOST_URL} \
                                -Dsonar.login=${SONAR_TOKEN}'
                        }
                    }
                }
            }
        }
        stage('Docker up') {
            when { branch 'develop' }
            steps {
                echo 'Running on Docker'
                sh 'docker network disconnect rhtotal_rhtotalnet postgres || true'
                sh 'docker-compose down --rmi all'
                sh 'docker-compose up -d'
                sh 'docker network connect rhtotal_rhtotalnet postgres || true'
            }
        }
        stage('Liquibase') {
            when { branch 'develop' }
            steps {
                sleep 10
                echo 'Running Liquibase migrations'
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
