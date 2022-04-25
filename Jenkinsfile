pipeline {
    agent any
    
     tools {
        maven "Maven"
    }
    stages {
         stage("Clone code from VCS") {
          steps {
               script {
                  git 'https://github.com/BlackRock2308/cicd_gestion_site.git';
                }
            }
        }
        
        stage('Checkout') {
            steps {
                echo 'Checkout'
            }
        }
        stage('Build') {
            steps {
                script {
                    echo 'Clean Build'
                    bat 'mvn clean compile'
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    echo 'Testing'
                    bat 'mvn test'
                }
            }
        }
        stage('JaCoCo') {
            steps {
                script {
                    echo 'Code Coverage'
                    jacoco()
                }
            }
        }
        stage('Sonar') {
            steps {
                script {
                         echo 'Sonar Scanner'
                         def scannerHome = tool 'My SonarQube Server';
                         withSonarQubeEnv('My SonarQube Server') {
                         bat "${scannerHome}/bin/sonar-scanner \
                         -D sonar.login=admin \
                         -D sonar.password=Lifeisagift30 \
                         -D sonar.projectKey=gestion-site-cicd-sonar \
                         -D sonar.language=java \

                         -D sonar.exclusions=vendor/**,resources/**,**/*.java \
                         -D sonar.sources=cicd_gestion_site/scr/main \
                         -D sonar.tests=cicd_gestion_site/scr/test \
                         -D sonar.host.url=http://192.168.56.1:9000/"
                    }
                }
            }
        }
        stage('Package') {
            steps {
                script {
                    echo 'Packaging'
                    bat 'mvn package -DskipTests'
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    echo '## TODO DEPLOYMENT ##'
                }
            }
        }
    }
    
    post {
        always {
            echo 'JENKINS PIPELINE'
        }
        success {
            echo 'JENKINS PIPELINE SUCCESSFUL'
        }
        failure {
            echo 'JENKINS PIPELINE FAILED'
        }
        unstable {
            echo 'JENKINS PIPELINE WAS MARKED AS UNSTABLE'
        }
        changed {
            echo 'JENKINS PIPELINE STATUS HAS CHANGED SINCE LAST EXECUTION'
        }
    }
}
