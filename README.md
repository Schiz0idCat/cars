El proyecto se centra en la creación de un videojuego de autos que esquiva a la polica y recolecta dinero.

* [Instalación](#Instalación)
    * [Dependencias](#Dependencias)
    * [Descarga](#Descarga)
    * [Compilación](#Compilación)
    * [Ejecución](#Ejecución)
* [Uso](#Uso)
    * [Controles](#Controles)
    * [Gameplay](#Gameplay)
* [Créditos](#Créditos)

# Instalación
## Dependencias
- openJDK 17
- gradle 9.2.0

## Descarga
```bash
git clone https://www.github.com/schiz0idcat/cars.git cars
cd cars
```

## Compilación
```bash
./gradlew build # compilación
java -jar lwjgl3/build/libs/GameLluviaMenu2024-1.0.0.jar # ejecutar el .jar
```

## Ejecución
```bash
./gradlew run # ejecutar desde el código fuente
```

# Uso
## Controles
```toml
[move]
left = "A / ←"
right = "D / →"

[system]
start = "Enter / Click"
exit = "ESC"
pause = "P"
resume = "ENTER / Click"
restart = "ENTER"

```
Para pausar presiona P.

Para reanudar, haz clic o presiona ENTER.

En el menú principal, puedes iniciar con ENTER o clic.

En la pantalla de Game Over, presiona ENTER para volver a jugar o ESC para salir.

## Gameplay
Los autos de policía hacen daño, mientras que las bolsas de dinero suman puntos.
El objetivo del juego es evitar a los policías y recoltectar la mayor cantidad de bolsas de dinero posible.

# Créditos
Este proyecto fue realizado por:
- Agustín Guzmán [[GitHub](https://github.com/Schiz0idCat)]
- Nicolás Leiva [[GitHub](https://github.com/nico0417)]
- Felipe Márquez [[GitHub](https://github.com/fmarquezmu)]
