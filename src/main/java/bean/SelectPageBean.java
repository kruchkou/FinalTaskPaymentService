package bean;

//Это будет Пользовательский тээээг!
public class SelectPageBean {
    int currentPage = 1;

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void nextPage() {
        currentPage++;
    }

    public void prevPage() {
        if(currentPage > 0) {
            currentPage--;
        }
    }

}
