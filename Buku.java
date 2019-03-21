package perpus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class Buku{
    //atribut object buku
    public String isbn, judulBuku, pengarang, penerbit, jumlahHalaman;
    int  jumlahBuku;

    //field table buku
    protected String table = "buku";
    protected String primaryKey = "ISBN";
    protected String[] fields = {"judulBuku","pengarang","jmlBuku"};
    
    //function
    public Connection connect(){
        Connection koneksi = null;
        String db_path = "jdbc:sqlite:DB/db_perpus.db";
        try{
            koneksi = DriverManager.getConnection(db_path);
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return koneksi;
    }

    public void getAll(){
        String sql = "SELECT * FROM "+this.table+" ORDER BY "+primaryKey+" ASC;";
        try(
            Connection konek = this.connect();
            Statement stmt = konek.createStatement();
            ResultSet data = stmt.executeQuery(sql);
            ) {
                //loop
                while(data.next()){
                    String isbn = data.getString("ISBN");
                    String jdl = data.getString("judulBuku");
                    String pengarang = data.getString("pengarang");
                    int stok = data.getInt("jmlBuku");

                    System.out.println("ISBN        : " + isbn);
                    System.out.println("Judul       : " + data.getString("judulbuku"));
                    System.out.println("Pengarang   : " + pengarang);
                    System.out.println("Jumlah      : " + stok+"\n");
                }
        } catch (SQLException e) {
            System.out.println("data buku gagal ditampilkan !");
            System.out.println(e.getMessage());
        }
    }

    public void getRow(String PK){
        String sql = "SELECT * FROM "+this.table+" WHERE "+this.primaryKey+" = '"+PK+"' LIMIT 1;";
        try(
            Connection konek = this.connect();
            Statement stmt = konek.createStatement();
            ResultSet data = stmt.executeQuery(sql);
            ) {
                while(data.next()){
                    this.isbn = data.getString("ISBN");
                    this.judulBuku = data.getString("judulBuku");
                    this.pengarang = data.getString("pengarang");
                    this.jumlahBuku = data.getInt("jmlBuku");
                }
          
        } catch (SQLException e) {
            System.out.println("buku tidak ada !");
            System.out.println(e.getMessage());
        }
    }

    public void insertData(String isbn, String judul, String pengarang, int jumlah){
        String sql = "INSERT INTO"+this.table+" values('"+isbn+"','"+judul+"','"+pengarang+"',"+jumlah+");";
        try(
            Connection konek = this.connect();
            Statement stmt = konek.createStatement()
            ) {
            stmt.execute(sql);
            System.out.println(sql);
            System.out.println("data berhasil disimpan !");
        } catch (SQLException e) {
            System.out.println("data gagal disimpan !");
            System.out.println(e.getMessage());
        }
    }

    public void update(String pk,String isbn, String judul, String pengarang, int jumlah ){
        String sql = "UPDATE "+this.table+" SET "+fields[0]+"='"+judul+"',"+fields[1]+"='"+pengarang+"',"+fields[0]+"="+jumlah+" WHERE "+this.primaryKey+"='"+pk+"';";
        try(
            Connection konek = this.connect();
            Statement stmt = konek.createStatement()
            ) {
            stmt.execute(sql);
            System.out.println(sql);
            System.out.println("data berhasil diubah !");
        } catch (SQLException e) {
            System.out.println("data gagal diubah !");
            System.out.println(e.getMessage());
        }
    }

    public void delete(String pk){
        String sql = "DELETE FROM "+this.table+" WHERE "+this.primaryKey+"' = "+pk+";";
        try(
            Connection konek = this.connect();
            Statement stmt = konek.createStatement()
            ) {
            stmt.execute(sql);
            System.out.println(sql);
            System.out.println("data berhasil dihapus !");
        } catch (SQLException e) {
            System.out.println("data gagal dihapus !");
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args){
        Buku buku = new Buku();
        buku.getAll();
    }
    
}