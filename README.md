<h1 align="center">
	<img
		width="300"
		alt="Logo Web Crawler"
		src="https://i.imgur.com/mKsad8h.png">
	<p>Web Crawler :globe_with_meridians: </p>
</h1>

<h3 align="center">
	A modern web crawler for your pages
</h3>

<p align="center">
	<strong>
		<a href="https://wiki.mta.ro/c/4/ip/lab/tema1-2020">Wiki Requirements</a>
		•
		<a href="https://google.com">Docs</a>
		•
		<a href="https://google.com">Demo</a>
	</strong>
</p>


<p align="center">
	<a href="https://github.com/GabrielStoica/WebCrawler/tree/master/src"><img
		alt="Build Status"
		src="https://github.com/thelounge/thelounge/workflows/Build/badge.svg"></a>
	<a href="https://github.com/GabrielStoica/WebCrawler"><img
		alt="Progress status"
		src="https://img.shields.io/badge/Progress-under%20construction-yellow"></a>
</p>


## Descriere :page_facing_up:

Un crawler web sau spider descarcă și indexează conținutul de pe paginile web disponibile într-o rețea precum Internetul. De cele mai multe ori un crawler web este operat de un motor de căutare și aplică un algoritm de colectare a datelor. Ulterior, prin aplicarea unor algoritmi de căutare în baza de date astfel construită, motoarele returnează utilizatorilor rezultate relevante pentru cuvintele-cheie introduse. 
 
Proiectul de față este conceput pentru a răspunde cerințelor Temei 1 din cadrul laboratorului de Ingineria Programării și va replica în scop demonstrativ comportamentul unui crawler folosit pentru indexarea paginilor Web și identificarea anumitor informații necesare.

## Instalare :wrench:

**Linux** Folosind managerul de pachete apt-get \
**Windows** Folosind installer-ul 

```bash
sudo apt-get install web-crawler
```

## Utilizare :satellite:

Fișier de configurare:

```text
**config.conf**
n_threads=50
delay=100ms
root_dir=D:/Download
log_level=3
depth_level=3
max_size=500kB
files_type=all/png/jpg/js/php
```

Exemplu metodă Java:

```java

public Crawl(String sitesFilename, Configuration config) throws FileNotFoundException {
        this._sitesFilename = sitesFilename;
        this._config = config;
    }
```

Exemplu cod Powershell:
```powershell
Copy-Item "C:\Logfiles" -Destination "C:\Drawings\Logs" -Recurse

crawler crawl config.conf sites.txt

crawler sitemap D:/Download/numesite1.ro
```

## Resurse :books:
-----

- [Lots of readme.md examples][1]
- [Yet another example][2]
- [Emojis][3]
- ["Shields" badges][4]

[1]: https://github.com/matiassingers/awesome-readme
[2]: https://www.makeareadme.com/
[3]: https://gist.github.com/rxaviers/7360908
[4]: https://shields.io/

## License
[GPL 3.0](https://www.gnu.org/licenses/gpl-3.0.html)
