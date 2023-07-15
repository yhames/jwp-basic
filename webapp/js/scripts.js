$(".qna-comment").on("click", ".form-delete", deleteAnswer);

function deleteAnswer(e) {
    console.log("deleteAnswer");
    e.preventDefault();
    $.ajax({
        type: 'post',
        url: '/api/qna/deleteAnswer',
        data: $(this).closest("form").serialize(),
        dataType: 'json',
        error: onError,
        success: (json, status) => {
            let result = json.result;
            if (result.status) {
                let count = parseInt($(".qna-comment-count strong").text());
                $(".qna-comment-count strong").text(count - 1);
                $(this).closest("article").remove();
            }
        },
    })
}

$(".answerWrite input[type=submit]").click(addAnswer);

function addAnswer(e) {
    e.preventDefault();

    var queryString = $("form[name=answer]").serialize();

    $.ajax({
        type: 'post',
        url: '/api/qna/addAnswer',
        data: queryString,
        dataType: 'json',
        error: onError,
        success: onSuccess,
    });
}

function onSuccess(json, status) {
    var answer = json.answer;
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(answer.writer, new Date(answer.createdDate), answer.contents, answer.answerId, answer.questionId);
    $(".qna-comment-slipp-articles").prepend(template);

    let count = parseInt($(".qna-comment-count strong").text());
    $(".qna-comment-count strong").text(count + 1);
}

function onError(xhr, status) {
    alert("error");
}

String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};