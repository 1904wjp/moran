$(document).ready(function (){
    testEdit();
});

function testEdit(){
    testEditor = editormd.markdownToHTML("article_content_info", {//注意：这里是上面DIV的id
        htmlDecode: "style,script,iframe",
        emoji: true,
        taskList: true,
        tocm: true,
        tex: true, // 默认不解析
        flowChart: true, // 默认不解析
        sequenceDiagram: true, // 默认不解析
        codeFold: true
    });
};