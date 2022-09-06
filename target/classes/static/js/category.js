
/**
 * Function to list all Categories
 */
async function listCategories() {
    await fetch("/SeriesManagement/category")
        .then(function (response) {
            if(response.status == 401) {
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
            path.innerHTML = "Home > Categories > All Categories"
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

            thead.appendChild(document.createElement("th")).appendChild(document.createTextNode("Name"));
            table.appendChild(thead);

            $.each(data, function (index, category) {

                var title = category.title;

                let row = table.insertRow(-1);

                let cell = row.insertCell(-1);
                cell.innerHTML = title;
                cell.classList.add('align-middle');
                cell.style.fontSize = "large";
                cell.onclick = function () {
                    console.log(category.catUUID);
                    loadCategoryUpdateForm(category.catUUID, title);
                }


            })
            container.appendChild(table);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * Function for loading the Form to create a Category
 */
function loadCategoryForm() {
    let container = document.getElementById("data");
    container.innerHTML = "<div class=\"container\">\n" +
        "    <div class=\"row\">\n" +
        "        <div class=\"col\"></div>\n" +
        "        <div class=\"col\">\n" +
        "            <form id=\"categoryForm\">\n" +
        "                <h2>create Category</h2>\n" +
        "                <div class=\"mb-3\">\n" +
        "                    <label for=\"title\">Title</label>\n" +
        "                    <br>\n" +
        "                    <input type=\"text\" name=\"title\" id=\"title\" required>\n" +
        "                </div>\n" +
        "                <br>\n" +
        "                <div class=\"mb-3\">\n" +
        "                    <input type=\"button\" class=\"btn btn-primary\" value=\"Save\" id=\"submit\" '>\n" +
        "                </div>" +
        "            </form>\n" +
        "        </div>\n" +
        "        <div class=\"col\"></div>\n" +
        "    </div>\n" +
        "</div>";

    let pathContainer = document.getElementById('path')
    pathContainer.innerHTML = "";
    let path = document.createElement('p');
    path.innerHTML = "Home > Categories > Create Category"
    pathContainer.appendChild(path);

    $("#submit").on('click', function () {
        const data = $("#categoryForm").serialize();

        fetch('/SeriesManagement/category', {
            method: 'POST', // or 'PUT'
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: data
        })
            .then(function (response) {
                //console.log(response.status);
                if(response.status == 401) {
                    sendLogout();
                }
                if (!response.ok) {
                    console.log("Invalid Input");
                    //console.log(response);
                } else {
                    //console.log("Erfolgreich");
                    listCategories();
                    return response;
                }
            })
            .catch(function (error) {
                alert("Error");
            });

    });

}

/**
 * Function to load the Form to update or delete a Category
 */
function loadCategoryUpdateForm(catUUID, name) {
    let container = document.getElementById("data");
    container.innerHTML = "<div class=\"container\">\n" +
        "    <div class=\"row\">\n" +
        "        <div class=\"col\"></div>\n" +
        "        <div class=\"col\">\n" +
        "            <form id=\"categoryForm\">\n" +
        "                <h2>Edit Category</h2>\n" +
        "                <div class=\"mb-3\">\n" +
        "                    <label for=\"title\">Title</label>\n" +
        "                    <br>\n" +
        "                    <input type=\"text\" name=\"title\" id=\"title\" required>\n" +
        "                </div>\n" +
        "                <br>\n" +
        "                <div class=\"mb-3\">\n" +
        "                    <input type=\"button\" class=\"btn btn-primary\" value=\"Save\" id=\"submit\" '>\n" +
        "                </div>" +
        "            </form>\n" +
        "        </div>\n" +
        "        <div class=\"col\"></div>\n" +
        "    </div>\n" +
        "</div>";

    let pathContainer = document.getElementById('path')
    pathContainer.innerHTML = "";
    let path = document.createElement('p');
    path.innerHTML = "Home > Categories > " + name;
    pathContainer.appendChild(path);

    let deleteS = document.createElement("button");
    deleteS.innerHTML = "Delete";
    deleteS.classList.add("btn");
    deleteS.classList.add("btn-danger");
    deleteS.onclick = function () {
        fetch('/SeriesManagement/category?uuid=' + catUUID, {
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
                    listCategories();
                }
            })
            .catch(function (error) {
                console.log(error);
            });
    }
    container.appendChild(deleteS);

    $("#submit").on('click', function () {
        const data = $("#categoryForm").serialize();

        fetch('/SeriesManagement/category?uuid=' + catUUID, {
            method: 'PUT', // or 'PUT'
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: data
        })
            .then(function (response) {
                //console.log(response.status);
                if(response.status == 401) {
                    sendLogout();
                }
                if (!response.ok) {
                    console.log("Invalid Input");
                    //console.log(response);
                } else {
                    //console.log("Erfolgreich");
                    listCategories();
                    return response;
                }
            })
            .catch(function (error) {
                alert("Error");
            });

    });

}

/**
 * Dropdown for creating a Series
 */
function categorySeriesForm() {

    fetch("/SeriesManagement/category")
        .then(function (response) {
            if(response.status == 401) {
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
            var select = document.getElementById('category');

            $.each(data, function(index, category) {
                let option = document.createElement('option');
                option.value = category.catUUID;
                option.id = category.catUUID;
                option.textContent = category.title;
                select.appendChild(option);
            })
        })
        .catch(function (error) {
            console.log(error);
        });
}