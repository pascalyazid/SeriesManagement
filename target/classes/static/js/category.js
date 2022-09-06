/**
 * Dropdown for creating a Series
 */
function categorySeriesForm() {

    fetch("/SeriesManagement/category")
        .then(function (response) {
            if(response.status == 403) {
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