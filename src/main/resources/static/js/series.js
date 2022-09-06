
/**
 * Function to list all Series
 */
function listSeries() {

    fetch("/SeriesManagement/series")
        .then(function (response) {
            if (response.status == 403) {
                sendLogout();
            }
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            let container = document.getElementById('data');
            container.innerHTML = "";
            let pathContainer = document.getElementById('path')
            pathContainer.innerHTML = "";
            let path = document.createElement('p');
            path.innerHTML = "Home > Series > All Series"
            pathContainer.appendChild(path);
            let br = document.createElement('br');
            pathContainer.appendChild(br);

            table = document.createElement('table');
            table.classList.add('table');
            table.classList.add('table-striped');
            while (table.hasChildNodes()) {
                table.removeChild(table.firstChild);
            }

            let thead = document.createElement('thead');

            thead.appendChild(document.createElement("th")).appendChild(document.createTextNode(""));
            thead.appendChild(document.createElement("th")).appendChild(document.createTextNode("Name"));
            thead.appendChild(document.createElement("th")).appendChild(document.createTextNode("Rating"));
            thead.appendChild(document.createElement("th")).appendChild(document.createTextNode("Episode Count"));
            thead.appendChild(document.createElement("th")).appendChild(document.createTextNode("Watched"));
            table.appendChild(thead);

            $.each(data, function (index, series) {
                var imgURL = series.imgURL;
                var title = series.title;
                var rating = series.rating;
                var episodes = series.episodeCount;
                var watched = (series.watched) ? 'Yes' : 'No';
                console.log(typeof imgURL);

                if (typeof imgURL === "undefined") {
                    imgURL = './images/defaultIMG.png';
                }

                let row = table.insertRow(-1);

                let cell = row.insertCell(-1);
                cell.innerHTML = "<img src=\"" + imgURL + "\"width=\"100px\" height=\"100px\">";
                cell.onclick = function () {
                    getSerie(serie.serieUUID);
                };

                cell = row.insertCell(-1);
                cell.innerHTML = title;
                cell.classList.add('align-middle');
                cell.style.fontSize = "large";

                cell = row.insertCell(-1);
                cell.innerHTML = rating;
                cell.classList.add('align-middle');
                cell.style.fontSize = "large";

                cell = row.insertCell(-1);
                cell.innerHTML = episodes;
                cell.classList.add('align-middle');
                cell.style.fontSize = "large";

                cell = row.insertCell(-1);
                cell.innerHTML = watched;
                cell.classList.add('align-middle');
                cell.style.fontSize = "large";


            })
            container.appendChild(table);
        })
        .catch(function (error) {
            console.log(error);
        });


}

function loadSeriesForm() {
    let container = document.getElementById("data");
    container.innerHTML = "<div class=\"container\">\n" +
        "    <div class=\"row\">\n" +
        "        <div class=\"col\"></div>\n" +
        "        <div class=\"col\">\n" +
        "            <form id=\"seriesForm\">\n" +
        "                <h2>Create Series</h2>\n" +
        "                <div class=\"mb-3\">\n" +
        "                    <label for=\"title\">Title</label>\n" +
        "                    <br>\n" +
        "                    <input type=\"text\" name=\"title\" id=\"title\" required>\n" +
        "                </div>\n" +
        "                <div class=\"mb-3\">\n" +
        "                    <label for=\"category\">category</label>\n" +
        "                    <br>\n" +
        "                    <select name=\"katUUID\" id=\"category\" class=\"select\" required></select>\n" +
        "                </div>\n" +
        "                <div class=\"mb-3\">\n" +
        "                    <label for=\"erschDatum\">Erscheinungsdatum</label>\n" +
        "                    <br>\n" +
        "                    <input type=\"date\" name=\"erschDatum\" id=\"erschDatum\" required>\n" +
        "                </div>\n" +
        "                <div class=\"mb-3\">\n" +
        "                    <label for=\"desc\">Description</label>\n" +
        "                    <br>\n" +
        "                    <input type=\"text\" name=\"desc\" id=\"desc\" required>\n" +
        "                </div>\n" +
        "                <div class=\"mb-3\">\n" +
        "                    <label for=\"episodeCount\">Anzahl Folgen</label>\n" +
        "                    <br>\n" +
        "                    <input type=\"number\" name=\"episodeCount\" id=\"episodeCount\" min=\"1\" max=\"9999\" required>\n" +
        "                </div>\n" +
        "                <div class=\"mb-3\">\n" +
        "                    <label for=\"imgURL\">Bild</label>\n" +
        "                    <br>\n" +
        "                    <input type=\"text\" name=\"imgURL\" id=\"imgURL\">\n" +
        "                </div>\n" +
        "                <div class=\"mb-3\">\n" +
        "                    <label for=\"rating\">rating</label>\n" +
        "                    <br>\n" +
        "                    <input type=\"number\" name=\"rating\" id=\"rating\" min=\"1\" max=\"10\">\n" +
        "                </div>\n" +
        "                <div class=\"mb-3\">\n" +
        "                    <label>watched</label>\n" +
        "                    <div>\n" +
        "                        <label for=\"false\">Nein</label>\n" +
        "                        <input type=\"radio\" id=\"false\" name=\"watched\" value=\"false\">\n" +
        "                        <br>\n" +
        "                        <label for=\"true\">Ja</label>\n" +
        "                        <input type=\"radio\" id=\"true\" name=\"watched\" value=\"true\">\n" +
        "                    </div>\n" +
        "                    <br>\n" +
        "                </div>\n" +
        "                <div class=\"mb-3\">\n" +
        "                    <input type=\"button\" class=\"btn btn-primary\" value=\"Save\" id=\"submit\" '>\n" +
        "                </div>" +
        "            </form>\n" +
        "        </div>\n" +
        "        <div class=\"col\"></div>\n" +
        "    </div>\n" +
        "</div>";
    categorySeriesForm();

    let pathContainer = document.getElementById('path')
    pathContainer.innerHTML = "";
    let path = document.createElement('p');
    path.innerHTML = "Home > Series > SeriesForm"
    pathContainer.appendChild(path);

    $("#submit").on('click', function () {
        const data = $("#serieForm").serialize();
        console.log(data);

        fetch('/SeriesManagement/series', {
            method: 'POST', // or 'PUT'
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: data
        })
            .then(function (response) {
                if (response.status == 403) {
                    sendLogout();
                }
                if (!response.ok) {
                    console.log("Invalid Input");
                    console.log(response);
                } else {
                    console.log("Successfull");
                    listSerien();
                    return response;
                }
            })
            .catch(function (error) {
                console.log(error);
            });

    });

}