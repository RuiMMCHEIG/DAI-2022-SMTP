# Configuration du serveur
Dans cette partie, nous verrons comment installer un serveur MockMock (https://github.com/DominiqueComte/MockMock) dans un container Docker.


## Prérequis
Il y a plusieurs prérequis avant de pouvoir commencer:

- Avoir Docker Desktop
- Activer Hyper-V afin de pouvoir utiliser Docker Desktop
- Installer "openjdk" depuis docker hub (https://hub.docker.com/_/openjdk) en effectuant la commande suivante: docker pull openjdk
- Télécharger les fichiers du repository suivant:
    [MockMock](https://github.com/DominiqueComte/MockMock)
    
Il est possible de télécharger directement un fichier .jar du serveur MockMock sur le github mentionné ci-dessus, néanmoins il n'est pas fonctionnel.

Il est alors nécessaire de compiler le projet et de générer un fichier .jar à l'aide de Maven en utilisant les options "Clean" et "Package".

Ce fichier .jar généré sera donc utilisable pour la suite de notre projet.
 
 
## Docker

### Configuration
    
Dès que openjdk sera installé nous allons créer et modifié un fichier nommé "Dockerfile" (sans extension), ce fichier sera utilisé pour la configuration d'une image docker.

Voici la configuration utilisée:

```
FROM openjdk:11
ADD MockMock-1.4.1-SNAPSHOT.one-jar.jar MockMock-1.4.1-SNAPSHOT.one-jar.jar
EXPOSE 8282
ENTRYPOINT ["java", "-jar", "MockMock-1.4.1-SNAPSHOT.one-jar.jar"]
```

Pour que cette configuration fonctionne correctement, il est nécessaire que le Dockerfile et le fichier jar se trouve dans le même répertoire sinon il faut modifier le Dockerfile en conséquence.

### Build

Dès que le fichier Dockerfile est prêt, il faudra aller dans un terminal et éxécuter les commandes suivantes:

- cd pathToYourDockerfileDirectory
- docker build -t mockmockserver ./
- Le résultat devrait être le suivant:

 ![](https://i.imgur.com/ND3Cjeg.png)
 
### Run

Maintenant que l'image mockmockserver a été créée, il faudra utiliser la commande docker run afin de pouvoir créer et démarrer un container qui sera basé sur l'image créée précédemment.

```
docker run -t mockmockserver
```

Si tout fonctionne correctement, le résultat devrait être le suivant:

![](https://i.imgur.com/MbHhFia.png)

A partir de là, le serveur MockMock sera utilisable.

## MockMock

Cette section contient quelques informations utiles sur MockMock.

De base, c'est le port par défaut 25 qui est utilisé pour la partie SMTP et le port 8282 pour la partie d'interface Web. Il est important de noter que sur certains système, des permissions root sont nécessaire pour écouter sur le port 25. 

Si le port 8282 est déjà utiliser sur votre machine, il est également possible de changer ça dans la configuration du Dockerfile.