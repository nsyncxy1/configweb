/*
 * Created by zmzhou on 2021/2/24 16:50
 */

/**
 * 加密
 * @param content 加密内容
 * @returns {*}
 */
import {getToken} from "@/utils/auth";
import CryptoJS from './crypto-js.min'
import $ from 'jquery'
export let encrypt = function (content) {
    let aseKey = "ws9ybUMn4F81t5oPKqJrqLKxERaYAS12"
    // eslint-disable-next-line no-undef
    return CryptoJS.AES.encrypt(content, CryptoJS.enc.Utf8.parse(aseKey), {
        // eslint-disable-next-line no-undef
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    }).toString();
}

export function showTips(msg) {
    $("#message_console").html(new Date().toLocaleTimeString() + "：" + msg);
}
// eslint-disable-next-line no-unused-vars
export function uploadFile(api) {
    let val = $('#current_path').val();
    // 设置上传路径
    $("#path").val(val);
    // 拼接所有上传文件名
    let files = $("#file").get(0).files;
    let fileNames = [];
    for (let i = 0; i < files.length; i++) {
        fileNames.push(files[i].name)
    }
    //获取form数据
    let formData = new FormData(document.querySelector("#upload_form"));
    $.ajax({
        url: api + "/sftp/upload",
        type: "POST",
        data: formData,
        processData: false,  // 不处理数据
        contentType: false,   // 不设置内容类型
        beforeSend: function(request) {
            request.setRequestHeader("Authorization",getToken());
        },
        success: function (res) {
            let tips = fileNames.join(",") + res.data;
            showTips(tips);
            let $tree = $('#file_tree').jstree(true);
            $tree.select_node(val);
            $tree.open_node($tree.get_node(val));
            alert(tips);
        }
    });
}
export function checkErr(data) {
    if (data.code === 200) {
        showTips("操作成功！");
        return true;
    }
    showTips("操作失败！" + data.msg);
    return false;
}

/**
 * 刷新数据
 */
export function refreshTree() {
    console.log('refreshTree:')
    let val = $('#current_path').val();
    let $tree = $('#file_tree').jstree(true);
    $tree.select_node(val);
    $tree.open_node($tree.get_node(val));
}
