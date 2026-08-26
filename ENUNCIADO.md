# Enunciados originales — Ficheros y directorios en Java

Este repositorio reúne varios bloques de ejercicios de **Acceso a Datos (2018)** sobre `Path`, ficheros/directorios, serialización de objetos y `Scanner`/`Formatter`.

## 1. Ficheros y directorios

1. Crear un `Path` con la carpeta de trabajo del usuario y mostrar ruta completa, nombre de carpeta, raíz, todos los componentes de la ruta, una ruta derivada añadiendo `leeme.txt` y la ruta desde la que se inició la aplicación.
2. Trabajar con `C:\Windows\system.ini`: comprobar permisos, copiarlo al directorio del usuario, resolver el conflicto al repetir la copia, renombrarlo sobrescribiendo si procede, mostrar su tamaño, borrarlo y crear `C:\FACTURAS\2012\ENERO`.
3. Copiar todos los ficheros `.ini` de `C:\WINDOWS` a la carpeta de usuario.
4. Listar los ficheros de la carpeta del usuario con permiso de escritura, su tamaño e indicar si son directorios.

### Ampliación

1. Mostrar los archivos con permiso de escritura y tamaño superior a 100 bytes.
2. Solicitar por teclado un fichero origen y un fichero destino y realizar la copia.
3. Crear un `Path` a partir de la ruta de inicio de la aplicación y recorrer sus componentes.
4. Crear la estructura de directorios indicada por la práctica en `C:\`, incluyendo `Tema1.txt` dentro de `ACCESS` y `Listado.txt` dentro de `WINDOWS`.

## 2. Ficheros de objetos

1. Leer temperaturas repetidamente hasta introducir `-100`, almacenarlas en un `ArrayList` y serializar la lista en `temperaturas.bin`.
2. Recuperar la lista, ordenarla, mostrar máximo, mínimo y media, y buscar una temperatura introducida por el usuario.
3. Crear contactos con DNI, nombre, apellidos y teléfono; implementar `equals` y `toString`; leerlos hasta introducir `*` como DNI y guardar el `ArrayList` en `contactos.bin`.
4. Recuperar `contactos.bin` y permitir ordenar, buscar por DNI y nombre, modificar, insertar en una posición, borrar por posición y volver a guardar la lista.

## 3. Escaneo y formateo de ficheros de texto

1. Contar las palabras de un fichero de texto multilínea.
2. Sumar enteros distribuidos en varias líneas y separados por espacios/tabuladores.
3. Procesar un CSV de líneas de factura (`artículo; pvp; unidades`), calcular el importe de cada línea y el total de la factura.
4. Procesar un CSV de comerciales de ACME y generar mediante `format` un informe de texto preparado para impresión, calculando una comisión del 7 %, totales, mes/año actuales y respetando los anchos de columna indicados.

No hay base de datos asociada a estos ejercicios: trabajan exclusivamente con el sistema de ficheros y formatos de texto/binario.
