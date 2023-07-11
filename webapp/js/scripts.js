$(".answerWrite input[type=submit]").click(addAnswer);

$(".qna-comment").on('click', ".form-delete", deleteAnswer);

function addAnswer(e) {
    e.preventDefault();
    $.ajax({
            type: "post",
            url: "/api/qna/addAnswer",
            data: $("form[name=answer]").serialize(),
            dataType: "json",
            error: onError,
            success: (json) => {
                let answerTemplate = $("#answerTemplate").html();
                let template = answerTemplate.format(json.writer, new Date(json.createdDate), json.contents, json.answerId);
                $(".qna-comment-slipp-articles").prepend(template);
            },
        }
    )
}

function deleteAnswer(e) {
    e.preventDefault();
    $.ajax({
        type: "post",
        url: "/api/qna/deleteAnswer",
        data: $(".form-delete").serialize(),
        dataType: "json",
        error: onError,
        success: (json) => {
            if (json.status) {
                $(this).closest("article").remove();
            }
        },
    });
}

function onError() {
    alert("error");
}


String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined' ? args[number] : match;
    });
};

