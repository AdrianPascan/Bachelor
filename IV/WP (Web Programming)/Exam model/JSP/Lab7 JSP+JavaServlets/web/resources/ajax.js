function getAssets(userId, callbackFunction) {
    $.ajax({
        type: "POST",
        url: "/assets",
        data: {
            action: "get",
            userid: userId
        },
        async: false,
        success: callbackFunction
    });
}

function saveAssets(userId, assets, callbackFunction) {
    $.ajax({
        type: "POST",
        url: "/assets",
        data: {
            action: "add",
            userid: userId,
            assets: addedAssets
        },
        async: false,
        success: callbackFunction
    });
}
