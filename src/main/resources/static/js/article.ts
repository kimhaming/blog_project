// 삭제 기능
const deleteButton = document.getElementById('delete-btn') as HTMLButtonElement;

if (deleteButton) {
    deleteButton.addEventListener('click', event => {
        let id = (document.getElementById('article-id') as HTMLInputElement).value;
        fetch(`/api/articles/${id}`, {
            method: 'DELETE'
        })
            .then(() => {
                alert('삭제가 완료되었습니다.');
                location.replace('/articles');
            });
    });
}

// 수정 기능
// id가 modify-btn인 엘리먼트 조회
const modifyButton = document.getElementById('modify-btn') as HTMLButtonElement;

if (modifyButton) {
    modifyButton.addEventListener('click', event => {
        const params = new URLSearchParams(location.search);
        const id = params.get('id');

        fetch(`/api/articles/${id}`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title: (document.getElementById('title') as HTMLInputElement).value,
                content: (document.getElementById('content')as HTMLInputElement).value
            })
        })
            .then(() => {
                alert('수정이 완료되었습니다.');
                location.replace(`/articles/${id}`);
            });
    });
}

// 등록 기능
// id가 create-btn인 엘리먼트
const createButton = document.getElementById('create-btn') as HTMLButtonElement;

if (createButton) {
    createButton.addEventListener("click", (event) => {
        fetch("/api/articles", {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title: (document.getElementById("title") as HTMLInputElement).value,
                content: (document.getElementById("content") as HTMLInputElement).value,
            }),
        }).then(() => {
            alert("등록 완료되었습니다.");
            location.replace("/articles");
        });
    });
}
