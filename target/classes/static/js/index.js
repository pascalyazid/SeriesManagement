$(document).ready(function () {
    loadNav();
    //isLoggedInI();
    //listSerien();
});
/**
 * Loads the Navbar
 */
function loadNav() {
    let navContainer = document.getElementById('navCont');
    navContainer.innerHTML = "      <nav class=\"navbar navbar-expand-lg navbar-light bg-light\">\n" +
        "        <div class=\"container-fluid\">\n" +
        "          <a class=\"navbar-brand\" href=\"#\">Home</a>\n" +
        "          <button class=\"navbar-toggler\" type=\"button\" data-bs-toggle=\"collapse\" data-bs-target=\"#navbarNavDropdown\" aria-controls=\"navbarNavDropdown\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n" +
        "            <span class=\"navbar-toggler-icon\"></span>\n" +
        "          </button>\n" +
        "          <div class=\"collapse navbar-collapse justify-content-end\" id=\"navbarNavDropdown\">\n" +
        "            <ul class=\"navbar-nav\">\n" +
        "              <li class=\"nav-item dropdown\">\n" +
        "                <a class=\"nav-link dropdown-toggle\" href=\"#\" id=\"navbarDropdownMenuLink\" role=\"button\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">\n" +
        "                  Kategorien\n" +
        "                </a>\n" +
        "                <ul class=\"dropdown-menu\" aria-labelledby=\"navbarDropdownMenuLink\">\n" +
        "                  <li><a class=\"dropdown-item\" href=\"javascript:loadKategorieForm()\">Kategorie erstellen</a></li>\n" +
        "                  <li><a class=\"dropdown-item\" href=\"javascript:listKategorien()\">Alle Kategorien</a></li>\n" +
        "                </ul>\n" +
        "              </li>\n" +
        "              <li class=\"nav-item dropdown\">\n" +
        "                <a class=\"nav-link dropdown-toggle\" href=\"#\" id=\"navbarDropdownMenuLink\" role=\"button\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">\n" +
        "                  Serien\n" +
        "                </a>\n" +
        "                <ul class=\"dropdown-menu\" aria-labelledby=\"navbarDropdownMenuLink\">\n" +
        "                  <li><a class=\"dropdown-item\" href=\"javascript:loadSeriesForm()\">Serie erstellen</a></li>\n" +
        "                  <li><a class=\"dropdown-item\" href=\"javascript:listSeries()\">Alle Serien</a></li>\n" +
        "                </ul>\n" +
        "              </li>\n" +
        "              <li class=\"nav-item dropdown\">\n" +
        "                <a class=\"nav-link dropdown-toggle\" href=\"#\" id=\"navbarDropdownMenuLink\" role=\"button\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">\n" +
        "                  Account\n" +
        "                </a>\n" +
        "                <ul class=\"dropdown-menu\" aria-labelledby=\"navbarDropdownMenuLink\">\n" +
        "                  <li><a class=\"dropdown-item\" href=\"#\">Mein Account</a></li>\n" +
        "                  <li><a class=\"dropdown-item\" href=\"javascript:sendLogout()\">Abmelden</a></li>\n" +
        "                </ul>\n" +
        "              </li>\n" +
        "            </ul>\n" +
        "          </div>\n" +
        "        </div>\n" +
        "      </nav>"

}