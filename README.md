# OT-Shiftplanner

Application for managing employees and work shifts.

## Project documentation

[Instructions](https://github.com/LauriKajakko/ot-harjoitustyo/blob/main/documentation/Instructions.md)

[Test Document](https://github.com/LauriKajakko/ot-harjoitustyo/blob/main/documentation/test_document.md)

[Project definition](https://github.com/LauriKajakko/ot-harjoitustyo/blob/main/documentation/definition.md)

[Hour Report](https://github.com/LauriKajakko/ot-harjoitustyo/blob/main/documentation/HourReport.md)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla. Testit luovat tietokannan "test.db".

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto _target/site/jacoco/index.html_


### JavaDoc

JavaDoc voidaan luoda komennolla

```
mvn javadoc:javadoc
```

JavaDocia voi tarkastella avaamalla selaimella tiedosto _target/site/apidocs/index.html_

### Checkstyle

```
 mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto _target/site/checkstyle.html_




