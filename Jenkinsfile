node {
dir("C:\\Users\\chara\\OneDrive\\Documents\\_Masters\\EGen\\THC-github\\THC") {
    stage("Run THC") {
        try {
            bat "docker-compose down"
            bat "docker system prune"
            bat "docker-compose up --build -d"
        } catch (e) {
            error "Service update failed"
        }
    }
}
}


//Copied initial Admin password from source location to desktop and access the file.