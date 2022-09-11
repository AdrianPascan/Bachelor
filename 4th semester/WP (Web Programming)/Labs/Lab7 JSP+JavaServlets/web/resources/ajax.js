function saveMove(userId, position, score, callbackFunction) {
    $.post("/stateInfo",
        {
            action: "saveMove",
            userid: userId,
            position: position,
            score: score
        },
        callbackFunction
    );
}

function deleteMoves(userId, callbackFunction) {
    $.post("/stateInfo",
        {
            action: "deleteMoves",
            userid: userId
        },
        callbackFunction
    );
}
