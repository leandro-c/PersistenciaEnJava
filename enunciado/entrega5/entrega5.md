## Entrega 5 - Actividad

En un esfuerzo por hacer al juego mas social los diseñadores del mismo han
decidido introducir un muro de actividades recientes, en el cuál un jugador
podrá ver la actividad que ha ocurrido cerca suyo recientemente..

Para eso se modelarán eventos. Todo evento ocurra en una ubicación especifica.
Por ahora se han identificado los siguientes eventos:

- Arribo: un jugador arriba a la localización.
- Captura: en una captura un jugador especifico atrapa un bicho de una determinada
especie.
- Abandono: un jugador abandona un bicho en una guardería.
- Coronacion: luego de un combate un jugador especifico se corona campeón de un
dojo destronando al campeón anterior.

## Se deberá implementar:
Un nuevo servicio llamado FeedService que implemente los siguientes mensajes:

1. El mensaje `feedEntrenador(String entrenador)` que devolverá la lista de
eventos que involucren al entrenador provisto. Esa lista incluirá eventos
relacionados a todos los viajes que haya hecho el entrenador (arribos), a
todas las capturas, a todos los bichos que haya abandonado y a todas las
coronaciones en las que haya sido coronado **o destronado**. Dicha lista debe
contener primero a los eventos más recientes.

2. El mensaje `feedUbicacion(String entrenador)` que devolverá el feed de eventos
principal que debe mostrarse al usuario. El mismo deberá incluír todas los
eventos de su ubicación actual y todos los eventos de las ubicaciones que estén
conectadas con la misma. Dicha lista debe contener primero a los eventos más
recientes.

## Se pide:
- El objetivo de esta entrega es implementar los requerimientos utilizando una
base de datos documental.
- Creen test unitarios para cada unidad de código entregada que prueben todas las
funcionalidades pedidas, con casos favorables y desfavorables.

### Consejos utiles:
- Traten de no tocar los objetos de modelo previamente definidos, solo van a
necesitar agregar las llamadas para crear nuevos eventos en los servicios del
TP2.
