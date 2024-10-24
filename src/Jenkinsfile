pipeline {
    agent any

    stages {
        stage("checkout") {
            steps {
                   checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/jux42/juxbar-backend.git']])
                echo "checkout done";
            }
        }

        stage("build") {
            steps {
                 echo "build done";
            }
        }


    }
}
