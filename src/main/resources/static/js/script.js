document.addEventListener("DOMContentLoaded", function() {
    let searchForm = document.querySelector('.main-form');

    function search() {
        event.preventDefault();

        // 검색어 가져오기
        let searchInput = document.querySelector('.searchText').value;

        // 장르 가져오기
        let selectedGenres = [];
        let genreCheckboxes = document.querySelectorAll('.genre-check-box .btn-check');
        genreCheckboxes.forEach(function(checkbox) {
            if (checkbox.checked) {
                selectedGenres.push(checkbox.value.trim());
            }
        });

        // 연도 가져오기
        let selectedYears = [];
        let yearCheckboxes = document.querySelectorAll('.yearBox1 .btn-check');
        yearCheckboxes.forEach(function(checkbox) {
            if (checkbox.checked) {
                selectedYears.push(checkbox.value.trim());
            }
        });

        // 폼 데이터를 생성하고, 선택된 장르와 연도를 추가합니다.
        let formData = new FormData(searchForm);
        formData.append('genre', selectedGenres.join(','));
        formData.append('movieYear', selectedYears.join(','));
    }
});
