$(document).ready(function () {
    // Обработчик клика по кнопкам редактирования
    $('.edit-button').on('click', function (event) {
        event.preventDefault(); // Предотвращение стандартного действия браузера
        //Отображение модального окна редактирования пользователя и загрузка его содержимого
        $('#user-profile').modal("show").find('.modal-dialog').load($(this).attr('href'), function(response, status, xhr) {
            if (xhr.status === 404) {
                // Если страница не найдена то перенаправляем на страницу администратора
                $(location).attr('href', '/admin');
            }
            // Обновление заголовка модального окна
            $('#user-profile .modal-header h3').text('Edit User');
            // Настройка кнопки сохранения
            let submit = $('#user-profile .modal-footer .submit');
            submit.text('Save'); // Изменение текста кнопки
            submit.addClass('btn-primary'); // Добавление класса для стилизации
            $('#user-profile #method').val("patch"); // Установка метода для формы редактирования
        });
    });
    // Обработчик клика по кнопкам удаления
    $('.delete-button').on('click', function (event) {
        event.preventDefault(); // Предотвращение стандартного действия браузера
        // Отображение модального окна удаления пользователя и загрузка его содержимого
        $('#user-profile').modal("show").find('.modal-dialog').load($(this).attr('href'), function(response, status, xhr) {
            if (xhr.status === 404) {
                // Если страница не найдена, перенаправляем на страницу администратора
                $(location).attr('href', '/admin');
            }
            // Обновление заголовка модального окна
            $('#user-profile .modal-header h3').text('Delete User');
            // Удаление поля ввода пароля и установка других полей только для чтения
            $('#user-profile #password-div').remove();
            $("#user-profile #firstName").prop("readonly", true);
            $("#user-profile #lastName").prop("readonly", true);
            $("#user-profile #age").prop("readonly", true);
            $("#user-profile #email").prop("readonly", true);
            $("#user-profile #roles").prop("disabled", true);
            // Настройка кнопки удаления
            let submit = $('#user-profile .modal-footer .submit');
            submit.text('Delete'); // Изменение текста кнопки
            submit.addClass('btn-danger'); // Добавление класса для стилизации
            $('#user-profile #method').val("delete"); // Установка метода для формы удаления
        });
    });
});
