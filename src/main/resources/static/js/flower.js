//加载个人信息
function loadSysUser() {
    $.ajax({
        async: false,
        type: "POST",
        data: {uid: id},
        url: '/userInfo/',
        dataType: "json",
        success: function (data) {
            // $("#selectRoleForm").empty();
            // var htm = "<input type='hidden' name='uid' value='" + id + "'>";
            // for (var i = 0; i < data.length; i++) {
            //     htm += "<div class='checkbox'><label><input type='checkbox' name='id' value='" + data[i].id + "'";
            //     if (data[i].selected == 1) {
            //         htm += " checked='checked'";
            //     }
            //     htm += "/>" + data[i].description + "</label></div>";
            // }
            $(".userInfo").empty();
            $(".userInfo").append("<input type='hidden' name='uid' id='uid' value='" + id + "'>");
            $("#roleTable tr:has(td)").remove();
            var html = "";
            for (var i = 0; i < data.length; i++) {
                html += "<tr><td class='text-center'><input type='checkbox' class='checkRole'  name='roleId' value='" + data[i].id + "'"
                if (data[i].selected == 1) {
                    html += " checked='checked'";
                }
                //角色是否可分配
                if (data[i].available == 0) {
                    html += "disabled ";
                }
                html += "/>" + "</td><td class='text-center'>" + data[i].id + "</td><td class='text-center'>" + data[i].role + "</td><td class='text-center'>" + data[i].description + "</td></tr>";
            }
            $("#roleTable").append(html)
        }
    });
    $('#selectRole').modal();

}