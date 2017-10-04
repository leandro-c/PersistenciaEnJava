## Entrega 6 - Cache

Se ha identificado que ciertas consultas en el sistema se realizan frecuentemente y consumen muchos recursos de los servidores de BBDD.

Se desea implementar una estrategia de cache, en donde ciertas consultas sean recuperadas previamente de una cache antes de acceder a la base de datos

Concretamente se quiere optimizar el accesso a las siguientes queries:

- `MapaService.cantidadEntrenadores(String ubicacion):int`. Se deberá consultar una Cache para recuperar la cantidad de entrenadores en esa ubicación.

- `LeaderboardService.campeones():List<Entrenador>`. Se deberá consutlar una Cache para recuperar los entrenadores campeones.

## Se pide:
- El objetivo de esta entrega es implementar los requerimientos utilizando una base de datos clave/valor (Infinispan, Redis) como cache.
- Creen test unitarios para cada unidad de código entregada que prueben todas las funcionalidades pedidas, con casos favorables y desfavorables.

### Consejos utiles:
- Traten de no tocar los objetos de modelo previamente definidos, solo van a necesitar agregar llamadas a la cache.
- Tengan en cuenta que deben mantener la consistencia de la cache. Es decir:
   - Siempre que un entrenador cambie su ubicación la cache de cantidadEntrenadores deberá cambiar (o invalidarse).
   - Siempre que un nuevo bicho sea coronado campeon la cache
   de campeones deberá cambiar (o invalidarse).
