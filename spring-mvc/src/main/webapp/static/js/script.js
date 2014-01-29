$(document).ready(function () {
    $("#list").tablesorter({
        headers: {
            0: {
                sorter: false
            }
        },
        widgets: ['zebra']
    });

    if ($('#deleteModal').html()) {
        var modal = $('#deleteModal').modal({
            keyboard: false,
            show: false
        });

        modal.on('click', '#nein', function (e) {
            e.preventDefault();
            modal.modal('hide');
        });

        modal.on('click', '#ja', function (e) {
            e.preventDefault();
            $('#deleteForm').submit();
        });
    }

    var iframe = $('#search-result');
    var url = iframe.attr('src').replace('*:*','');

    $('#search').on('keyup', function() {
        var val = $(this).val();
        iframe.attr('src', url + "name:"+val+"*")
    });
});