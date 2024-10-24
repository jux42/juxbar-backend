pipeline {
    agent any
    tools {
        maven 'Maven 3.9.9'
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
                echo "tests done"
            }
        }

            stage("build") {
                steps {
                    sh 'mvn clean package'
                    echo "build done"
                }
            }


        }
    }