document.getElementById('openModalBtn').addEventListener('click', function() {
    var modal = document.getElementById('myModal');
    modal.style.display = 'block';

    // 发送Ajax请求获取子页面内容
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                document.getElementById('modalContent').innerHTML = xhr.responseText;
            }
        }
    };
    xhr.open('GET', '/mall/product/toPopUpCart.html', true);
    xhr.send();
});

;
