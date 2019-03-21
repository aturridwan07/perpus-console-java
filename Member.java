package perpus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class Member{
    //atribut object petugas
    public String idMember, namaMember, alamatMember;

    //field table petugas
    protected String table = "member";
    protected String primaryKey = "idMember";
    //belum dipakai
    protected String[] fields = {"idMember","namaMember"};
    
    //koneksi ke database
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
                    String nip = data.getString("idMember");
                    String nama = data.getString("namaMember");
                    String alamat = data.getString("alamatMember");
                    
                    System.out.println("ID          : " + nip);
                    System.out.println("Nama        : " + nama);
                    System.out.println("Alamat      : " + alamat+"\n");
                }
        } catch (SQLException e) {
            System.out.println("data member gagal ditampilkan !");
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
                    this.idMember = data.getString("idMember");
                    this.namaMember = data.getString("namaMember");
                    this.alamatMember = data.getString("alamatMember");
                   
                }
          
        } catch (SQLException e) {
            System.out.println("member tidak ada !");
            System.out.println(e.getMessage());
        }
    }

    public void insertData(String id, String nama, String alamat){
        String sql = "INSERT INTO"+this.table+" values('"+id+"','"+nama+"','"+alamat+"');";
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

    public void update(String pk,String id, String nama, String alamat){
        String sql = "UPDATE "+this.table+" SET "+fields[0]+"='"+nama+"',"+fields[1]+"='"+alamat+"' WHERE "+this.primaryKey+"='"+pk+"';";
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
        String sql = "DELETE FROM "+this.table+" WHERE "+this.primaryKey+" = '"+pk+"';";
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
        Member member = new Member();
        member.getAll();
    }
}