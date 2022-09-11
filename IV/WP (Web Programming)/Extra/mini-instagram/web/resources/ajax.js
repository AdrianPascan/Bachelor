function AJAXgetPhotos(top, callbackFunction) {
    $.ajax({
        type: "POST",
        url: "/photos",
        data: {
            action: "getPhotos",
            top: top
        },
        // async: false,
        success: callbackFunction
    });
}

function AJAXaddPhoto(userId, source, callbackFunction) {
    $.ajax({
        type: "POST",
        url: "/photos",
        data: {
            action: "addPhoto",
            userid: userId,
            source: source
        },
        // async: false,
        success: callbackFunction
    });
}

function AJAXvotePhoto(userId, photoId, rank, callbackFunction) {
    $.ajax({
        type: "POST",
        url: "/photos",
        data: {
            action: "votePhoto",
            userid: userId,
            photoid: photoId,
            rank: rank
        },
        // async: false,
        success: callbackFunction
    });
}
