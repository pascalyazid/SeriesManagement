/**
 * Function to list all Series
 */
async function listSeries() {

    await fetch("/SeriesManagement/series")
        .then(function (response) {
            if (response.status == 401) {
                sendLogout();
            }
            if (response.ok) {
                return response;
            } else {
                //.log(response);
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
                //console.log(typeof imgURL);

                if (typeof imgURL === "undefined") {
                    imgURL = './images/defaultIMG.png';
                }

                let row = table.insertRow(-1);

                let cell = row.insertCell(-1);
                cell.innerHTML = "<img src=\"" + imgURL + "\"width=\"100px\" height=\"100px\">";
                cell.onclick = function () {
                    getSeries(series.seriesUUID);
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

async function loadSeriesForm() {
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

    $("#submit").on('click', async function () {
        const data = $("#seriesForm").serialize();
        //console.log(data);

        await fetch('/SeriesManagement/series', {
            method: 'POST', // or 'PUT'
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: data
        })
            .then(function (response) {
                if (response.status == 401) {
                    sendLogout();
                }
                if (!response.ok) {
                    console.log("Invalid Input");
                    console.log(response);
                } else {
                    //console.log("Successfull");
                    listSeries();
                    return response;
                }
            })
            .catch(function (error) {
                console.log(error);
            });

    });
}

/**
 * Function to show a single series
 * @param uuid
 */
 async function getSeries(uuid) {
    await fetch("/SeriesManagement/series/get?uuid=" + uuid)
        .then(function (response) {
            if (response.status == 401) {
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
            path.innerHTML = "Home > Series > " + data.title;
            pathContainer.appendChild(path);
            let br = document.createElement('br');
            pathContainer.appendChild(br);

            let field = document.createElement("div");
            field.innerHTML = "                <div className=\"col-3\"></div>\n" +
                "                <div className=\"col\">\n" +
                "                </div>\n" +
                "                <div className=\"col\"></div>\n";

            field.classList.add("row");
            field.classList.add("gx-2");

            let img = document.createElement("img");
            img.src = data.imgURL;
            img.width = 400;
            img.height = 400;

            field.appendChild(img);
            field.children.item(1).appendChild(img);
            container.appendChild(field);

            let field2 = field.children.item(2);
            let brr = document.createElement("br");

            let p = document.createElement("p");
            p.innerHTML = "Title: " + data.title;
            field2.appendChild(p);
            field2.appendChild(brr);

            p = p.cloneNode(true);
            p.innerHTML = "ReleaseDate: " + data.releaseDate;
            field2.appendChild(p);
            field2.appendChild(brr);

            p = p.cloneNode(true);
            p.innerHTML = "Description: " + data.desc;
            field2.appendChild(p);
            field2.appendChild(brr);

            p = p.cloneNode(true);
            p.innerHTML = "Episodes: " + data.episodeCount;
            field2.appendChild(p);
            field2.appendChild(brr);

            p = p.cloneNode(true);
            p.innerHTML = "Rating: " + data.rating + "/10</p>";
            field2.appendChild(p);
            field2.appendChild(brr);

            p = p.cloneNode(true);
            var watched = "No";
            if (data.watched) {
                watched = "Yes";
            }
            p.innerHTML = "Watched? " + watched;
            field2.appendChild(p);
            field2.appendChild(brr);

            let update = document.createElement("button");
            update.innerHTML = "Edit";
            update.onclick = function () {
                loadSeriesUpdateForm(data.seriesUUID, data.title);
            }
            update.classList.add("btn");
            update.classList.add("btn-warning");
            field2.appendChild(update);
            field2.appendChild(brr);

            let deleteS = document.createElement("button");
            deleteS.innerHTML = "Delete";
            deleteS.classList.add("btn");
            deleteS.classList.add("btn-danger");
            deleteS.onclick = async function () {
                await fetch('/SeriesManagement/series?uuid=' + data.seriesUUID, {
                    method: 'DELETE',
                    headers: {
                        'Content-type': 'application/json; charset=UTF-8' // Indicates the content
                    },
                })
                    .then(function (response) {
                        if (response.status == 401) {
                            sendLogout();
                        }
                        if (!response.ok) {
                            console.log(response)
                        } else {
                            listSeries();
                        }
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            }
            field2.appendChild(brr);
            field2.appendChild(deleteS);

        });

}

/**
 * Form to edit or delete a Series
 * @param serieUUID
 * @param name
 */
 function loadSeriesUpdateForm(seriesUUID, name) {
    let container = document.getElementById("data");
    container.innerHTML = "<div class=\"container\">\n" +
        "    <div class=\"row\">\n" +
        "        <div class=\"col\"></div>\n" +
        "        <div class=\"col\">\n" +
        "            <form id=\"serieForm\">\n" +
        "                <h2>Serie bearbeiten</h2>\n" +
        "                <div class=\"mb-3\">\n" +
        "                    <label for=\"title\">Title</label>\n" +
        "                    <br>\n" +
        "                    <input type=\"text\" name=\"title\" id=\"titel\" required>\n" +
        "                </div>\n" +
        "                <div class=\"mb-3\">\n" +
        "                    <label for=\"category\">Category</label>\n" +
        "                    <br>\n" +
        "                    <select name=\"catUUID\" id=\"category\" class=\"select\" required></select>\n" +
        "                </div>\n" +
        "                <div class=\"mb-3\">\n" +
        "                    <label for=\"releaseDate\">Release Date</label>\n" +
        "                    <br>\n" +
        "                    <input type=\"date\" name=\"\" id=\"releaseDate\" required>\n" +
        "                </div>\n" +
        "                <div class=\"mb-3\">\n" +
        "                    <label for=\"desc\">Description</label>\n" +
        "                    <br>\n" +
        "                    <input type=\"text\" name=\"desc\" id=\"desc\" required>\n" +
        "                </div>\n" +
        "                <div class=\"mb-3\">\n" +
        "                    <label for=\"episodeCount\">Episodes</label>\n" +
        "                    <br>\n" +
        "                    <input type=\"number\" name=\"episodeCount\" id=\"episodeCount\" min=\"1\" max=\"9999\" required>\n" +
        "                </div>\n" +
        "                <div class=\"mb-3\">\n" +
        "                    <label for=\"imgURL\">Image</label>\n" +
        "                    <br>\n" +
        "                    <input type=\"text\" name=\"imgURL\" id=\"imgURL\">\n" +
        "                </div>\n" +
        "                <div class=\"mb-3\">\n" +
        "                    <label for=\"rating\">Rating</label>\n" +
        "                    <br>\n" +
        "                    <input type=\"number\" name=\"rating\" id=\"rating\" min=\"1\" max=\"10\">\n" +
        "                </div>\n" +
        "                <div class=\"mb-3\">\n" +
        "                    <label>Watched</label>\n" +
        "                    <div>\n" +
        "                        <label for=\"false\">No</label>\n" +
        "                        <input type=\"radio\" id=\"false\" name=\"watched\" value=\"false\">\n" +
        "                        <br>\n" +
        "                        <label for=\"true\">Yes</label>\n" +
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
    path.innerHTML = "Home > Series > " + name
    pathContainer.appendChild(path);

    $("#submit").on('click', async function () {
        const data = $("#seriesForm").serialize();

        await fetch('/SeriesManagement/series?uuid=' + seriesUUID, {
            method: 'PUT', // or 'PUT'
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: data
        })
            .then(function (response) {
                if (response.status == 401) {
                    sendLogout();
                }
                if (!response.ok) {
                    console.log("Invalid Input");
                    console.log(response);
                } else {

                    //log("Success");
                    listSeries();
                    return response;
                }
            })
            .catch(function (error) {
                console.log(error);
            });

    });
}