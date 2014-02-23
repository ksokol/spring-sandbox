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

    if(iframe.html()) {
        var url = iframe.attr('src').replace('*:*','');

        $('#search').on('keyup', function() {
            var val = $(this).val();
            iframe.attr('src', url + "name:"+val+"*")
        });
    }

    var appendButton = $('#append');

    if(appendButton.html()) {
        var recalc = function() {
           var countries = $('.country');
           var size = countries.length;

           for(i=0;i<size;i++) {
                var country = $(countries[i]);
                country.data('idx', i);
                var val = 'locales['+i+']';
                country.find('.title').text(val);
                country.find('input').attr('name', val);
            };
        };

        recalc();

        appendButton.on('click', function(e) {
            e.preventDefault();
            var input = $('.country').first().clone();
            input.find('input').attr('value', '');

            input.insertAfter($('.country:last'));
            recalc();
        });

        $('.country .close').on('click', function(e) {
            e.preventDefault();
            $(this).parent().parent().remove();
            recalc();
        });
    }

});