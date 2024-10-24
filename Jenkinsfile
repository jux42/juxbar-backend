pipeline {
    agent any
    tools {
        maven 'Maven-3.9.9'
    }

    stages {
        stage("checkout") {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/jux42/juxbar-backend.git']])
                echo "checkout done"
            }
        }

        stage("test") {
            steps {
                sh 'mvn clean test'
                sh 'mvn surefire-report:report'
                echo "tests done"
            }
        }

            stage("build") {
                steps {
                    sh 'mvn clean package -DskipTests=true'
                    echo "build done"
                }
            }

        }
    post{
        always{
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            junit '/target/reports/surefire.html'
        }
    }
    }