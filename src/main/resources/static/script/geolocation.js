function getUserLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(displayPosition, displayError);
    } else {
        document.getElementById("locationInfo").innerHTML = "Geolocation is not supported by this browser.";
    }
}

function displayPosition(position) {
    document.getElementById("locationInfo").innerHTML = "Latitude: " + position.coords.latitude +
        "<br>Longitude: " + position.coords.longitude;
}

function displayError(error) {
    switch (error.code) {
        case error.PERMISSION_DENIED:
            document.getElementById("locationInfo").innerHTML = "User denied the request for Geolocation."
            break;
        case error.POSITION_UNAVAILABLE:
            document.getElementById("locationInfo").innerHTML = "Location information is unavailable."
            break;
        case error.TIMEOUT:
            document.getElementById("locationInfo").innerHTML = "The request to get user location timed out."
            break;
        case error.UNKNOWN_ERROR:
            document.getElementById("locationInfo").innerHTML = "An unknown error occurred."
            break;
    }
}