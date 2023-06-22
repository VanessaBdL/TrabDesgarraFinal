package br.univali.desgarra.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.RadialGradient;

import java.util.ArrayList;
import br.univali.desgarra.inicial.Usuario;
import br.univali.desgarra.menu.Pelucia;
import br.univali.desgarra.menu.Transacao;

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private static final String DATABASE_NAME = "desgarra";

    private static final String TABLE_USUARIO = "usuario";

    private static final String TABLE_PELUCIA = "pelucia";

    private static final String TABLE_TRANSACAO = "transacao";
    private static final String CREATE_TABLE_PELUCIA = "CREATE TABLE " + TABLE_PELUCIA + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "id_usuario INTEGER, " +
            "nome VARCHAR(50), " +
            "preco DECIMAL(10,2), " +
            "tamanho DECIMAL(10,2), " +
            "descricao VARCHAR(100), " +
            "cor VARCHAR(100), " +
            "data_aquisicao VARCHAR(10), " +
            "vender VARCHAR(1), " +
            "trocar VARCHAR(1), " +
            "doar VARCHAR(1));";

    private static final String DROP_TABLE_PELUCIA = "DROP TABLE IF EXISTS " + TABLE_PELUCIA;

    private static final String CREATE_TABLE_USUARIO = "CREATE TABLE " + TABLE_USUARIO + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nome VARCHAR(50), " +
            "email VARCHAR(50), " +
            "senha VARCHAR(30), " +
            "data_nascimento VARCHAR(10), " +
            "telefone VARCHAR(15), " +
            "endereco VARCHAR(50), " +
            "numero INTEGER, " +
            "complemento VARCHAR(15), " +
            "cep VARCHAR(15), " +
            "rg VARCHAR(15), " +
            "cpf VARCHAR(15), " +
            "chave_pix VARCHAR(100));";

    private static final String DROP_TABLE_USUARIO = "DROP TABLE IF EXISTS " + TABLE_USUARIO;

    private static final String CREATE_TABLE_TRANSACAO = "CREATE TABLE " + TABLE_TRANSACAO + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "id_usuario_vendedor INTEGER, " +
            "id_pelucia INTEGER, " +
            "id_usuario_recebedor INTEGER, " +
            "tipo_transacao VARCHAR(15));";

    private static final String DROP_TABLE_TRANSACAO = "DROP TABLE IF EXISTS " + TABLE_TRANSACAO;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USUARIO);
        db.execSQL(CREATE_TABLE_PELUCIA);
        db.execSQL(CREATE_TABLE_TRANSACAO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_USUARIO);
        db.execSQL(DROP_TABLE_PELUCIA);
        db.execSQL(DROP_TABLE_TRANSACAO);
        onCreate(db);
    }

    public void closeDBConnection() {
        db.close();
    }

    /* Início CRUD Pelucia */
    public long createPelucia (Pelucia p) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_usuario", p.getId_usuario());
        cv.put("nome", p.getNome());
        cv.put("preco", p.getPreco());
        cv.put("tamanho", p.getTamanho());
        cv.put("descricao", p.getDescricao());
        cv.put("cor", p.getCor());
        cv.put("data_aquisicao", p.getData_aquisicao());
        cv.put("vender", p.getVender());
        cv.put("trocar", p.getTrocar());
        cv.put("doar", p.getDoar());
        long id = db.insert(TABLE_PELUCIA, null, cv);
        db.close();
        return id;
    }

    public long updatePelucia (Pelucia p) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_usuario", p.getId_usuario());
        cv.put("nome", p.getNome());
        cv.put("preco", p.getPreco());
        cv.put("tamanho", p.getTamanho());
        cv.put("descricao", p.getDescricao());
        cv.put("cor", p.getCor());
        cv.put("data_aquisicao", p.getData_aquisicao());
        cv.put("vender", p.getVender());
        cv.put("trocar", p.getTrocar());
        cv.put("doar", p.getDoar());
        long id = db.update(TABLE_PELUCIA, cv, "_id = ?", new String[]{String.valueOf(p.getId())});
        db.close();
        return id;
    }

    public long deletePelucia (Pelucia u) {
        db = this.getWritableDatabase();
        long id = db.delete(TABLE_PELUCIA, "_id = ?", new String[]{String.valueOf(u.getId())});
        db.close();
        return id;
    }

    public Pelucia getByIdPelucia (int id) {
        db = this.getReadableDatabase();
        String[] columns = {"_id","id_usuario","nome","preco","tamanho","descricao","cor","data_aquisicao","vender","trocar","doar"};
        Cursor data = db.query(TABLE_PELUCIA, columns, "_id = ?", new String[]{String.valueOf(id)}, null, null, null);

        data.moveToFirst();
        Pelucia p = new Pelucia();
        p.setId(data.getInt(0));
        p.setId_usuario(data.getInt(1));
        p.setNome(data.getString(2));
        p.setPreco(data.getFloat(3));
        p.setTamanho(data.getFloat(4));
        p.setDescricao(data.getString(5));
        p.setCor(data.getString(6));
        p.setData_aquisicao(data.getString(7));
        p.setVender(data.getString(8));
        p.setTrocar(data.getString(9));
        p.setDoar(data.getString(10));

        data.close();
        db.close();
        return p;
    }

    public Cursor getAllPelucia () {
        db = this.getReadableDatabase();
        String[] columns = {"_id","id_usuario","nome","preco","tamanho","descricao","cor","data_aquisicao","vender","trocar","doar"};
        return db.query(TABLE_PELUCIA, columns, null, null, null, null, null);
    }

    public Cursor getAllPeluciaNotUsuario (int idUsuario) {
        db = this.getReadableDatabase();
        String[] columns = {"_id","id_usuario","nome","preco","tamanho","descricao","cor","data_aquisicao","vender","trocar","doar"};
        String selection = "id_usuario <> ?";
        String[] selectionArgs = {String.valueOf(idUsuario)};
        return db.query(TABLE_PELUCIA, columns, selection, selectionArgs, null, null, null);
    }
    public Cursor getAllPeluciaUsuario (int idUsuario) {
        db = this.getReadableDatabase();
        String[] columns = {"_id","id_usuario","nome","preco","tamanho","descricao","cor","data_aquisicao","vender","trocar","doar"};
        String selection = "id_usuario = ?";
        String[] selectionArgs = {String.valueOf(idUsuario)};
        return db.query(TABLE_PELUCIA, columns, selection, selectionArgs, null, null, null);
    }

    public void getAllNamePelucia (ArrayList<Integer> listPeluciaId, ArrayList<String> listPeluciaName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome"};
        Cursor data = db.query(TABLE_PELUCIA, columns, null, null, null,
                null, "nome");
        while (data.moveToNext()) {
            int idColumnIndex = data.getColumnIndex("_id");
            listPeluciaId.add(Integer.parseInt(data.getString(idColumnIndex)));
            int nameColumnIndex = data.getColumnIndex("nome");
            listPeluciaName.add(data.getString(nameColumnIndex));
        }
        db.close();
    }

    /* Fim CRUD Pelucia */

    /* Início CRUD Usuario */
    public long createUsuario (Usuario u) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", u.getNome());
        cv.put("email", u.getEmail());
        cv.put("senha", u.getSenha());
        cv.put("data_nascimento", u.getData_nascimento());
        cv.put("telefone", u.getTelefone());
        cv.put("endereco", u.getEndereco());
        cv.put("numero", u.getNumero());
        cv.put("complemento", u.getComplemento());
        cv.put("cep", u.getCep());
        cv.put("rg", u.getRg());
        cv.put("cpf", u.getCpf());
        cv.put("chave_pix", u.getChave_pix());
        long id = db.insert(TABLE_USUARIO, null, cv);
        db.close();
        return id;
    }

    public long updateUsuario (Usuario u) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", u.getNome());
        cv.put("email", u.getEmail());
        cv.put("senha", u.getSenha());
        cv.put("data_nascimento", u.getData_nascimento());
        cv.put("telefone", u.getTelefone());
        cv.put("endereco", u.getEndereco());
        cv.put("numero", u.getNumero());
        cv.put("complemento", u.getComplemento());
        cv.put("cep", u.getCep());
        cv.put("rg", u.getRg());
        cv.put("cpf", u.getCpf());
        cv.put("chave_pix", u.getChave_pix());
        long id = db.update(TABLE_USUARIO, cv, "_id = ?", new String[]{String.valueOf(u.getId())});
        db.close();
        return id;
    }

    public long deleteUsuario (Usuario u) {
        db = this.getWritableDatabase();
        long id = db.delete(TABLE_USUARIO, "_id = ?", new String[]{String.valueOf(u.getId())});
        db.close();
        return id;
    }

    public Usuario getByIdUsuario (int id) {
        db = this.getReadableDatabase();
        String[] columns = {"_id", "nome", "email","senha","data_nascimento","telefone","endereco","numero","complemento","cep","rg","cpf", "chave_pix"};
        Cursor data = db.query(TABLE_USUARIO, columns, "_id = ?", new String[]{String.valueOf(id)}, null, null, null);

        data.moveToFirst();
        Usuario u = new Usuario();
        u.setId(data.getInt(0));
        u.setNome(data.getString(1));
        u.setEmail(data.getString(2));
        u.setSenha(data.getString(3));
        u.setData_nascimento(data.getString(4));
        u.setTelefone(data.getString(5));
        u.setEndereco(data.getString(6));
        u.setNumero(data.getInt(7));
        u.setComplemento(data.getString(8));
        u.setCep(data.getString(9));
        u.setRg(data.getString(10));
        u.setCpf(data.getString(11));
        u.setChave_pix(data.getString(12));

        data.close();
        db.close();
        return u;
    }

    public Cursor getAllUsuario () {
        db = this.getReadableDatabase();
        String[] columns = {"_id", "nome", "email","senha","data_nascimento","telefone","endereco","numero","complemento","cep","rg","cpf", "chave_pix"};
        return db.query(TABLE_USUARIO, columns, null, null, null, null, null);
    }

    public void getAllNameUsuario (ArrayList<Integer> listUsuarioId, ArrayList<String> listUsuarioName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome"};
        Cursor data = db.query(TABLE_USUARIO, columns, null, null, null,
                null, "nome");
        while (data.moveToNext()) {
            int idColumnIndex = data.getColumnIndex("_id");
            listUsuarioId.add(Integer.parseInt(data.getString(idColumnIndex)));
            int nameColumnIndex = data.getColumnIndex("nome");
            listUsuarioName.add(data.getString(nameColumnIndex));
        }
        db.close();
    }

    /* Fim CRUD Usuario */

    /* Início CRUD Transação */
    public long createTransacao (Transacao t) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_usuario_vendedor", t.getId_usuario_vendedor());
        cv.put("id_pelucia", t.getId_pelucia());
        cv.put("id_usuario_recebedor", t.getId_usuario_recebedor());
        cv.put("tipo_transacao", t.getTipo_transacao());
        long id = db.insert(TABLE_TRANSACAO, null, cv);
        db.close();
        return id;
    }

    public long updateTransacao (Transacao t) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_usuario_vendedor", t.getId_usuario_vendedor());
        cv.put("id_pelucia", t.getId_pelucia());
        cv.put("id_usuario_recebedor", t.getId_usuario_recebedor());
        cv.put("tipo_transacao", t.getTipo_transacao());
        long id = db.update(TABLE_TRANSACAO, cv, "_id = ?", new String[]{String.valueOf(t.getId())});
        db.close();
        return id;
    }

    public long deleteTransacao (Transacao t) {
        db = this.getWritableDatabase();
        long id = db.delete(TABLE_TRANSACAO, "_id = ?", new String[]{String.valueOf(t.getId())});
        db.close();
        return id;
    }

    public Transacao getByIdTransacao (int id) {
        db = this.getReadableDatabase();
        String[] columns = {"_id","id_usuario_vendedor","id_pelucia","id_usuario_recebedor","tipo_transacao"};
        Cursor data = db.query(TABLE_TRANSACAO, columns, "_id = ?", new String[]{String.valueOf(id)}, null, null, null);

        data.moveToFirst();
        Transacao t = new Transacao();
        t.setId(data.getInt(0));
        t.setId_usuario_vendedor(data.getInt(1));
        t.setId_pelucia(data.getInt(2));
        t.setId_usuario_recebedor(data.getInt(3));
        t.setTipo_transacao(data.getString(4));

        data.close();
        db.close();
        return t;
    }

    public Cursor getAllTransacao () {
        db = this.getReadableDatabase();
        String[] columns = {"_id","id_usuario_vendedor","id_pelucia","id_usuario_recebedor","tipo_transacao"};
        return db.query(TABLE_TRANSACAO, columns, null, null, null, null, null);
    }

    public Cursor getAllTransacaoUsuarioVenda (int idUsuario) {
        db = this.getReadableDatabase();
        String[] columns = {"_id","id_usuario_vendedor","id_pelucia","id_usuario_recebedor","tipo_transacao"};
        String selection = "id_usuario_vendedor = ?";
        String[] selectionArgs = {String.valueOf(idUsuario)};
        return db.query(TABLE_TRANSACAO, columns, selection, selectionArgs, null, null, null);
    }
    public Cursor getAllTransacaoUsuarioRecebe (int idUsuario) {
        db = this.getReadableDatabase();
        String[] columns = {"_id","id_usuario_vendedor","id_pelucia","id_usuario_recebedor","tipo_transacao"};
        String selection = "id_usuario_recebedor = ?";
        String[] selectionArgs = {String.valueOf(idUsuario)};
        return db.query(TABLE_TRANSACAO, columns, selection, selectionArgs, null, null, null);
    }

    public void getAllNamePeluciaTransacao (int idUsuario, ArrayList<Integer> listPeluciaId) {
        SQLiteDatabase db = this.getWritableDatabase();


        String[] columns = {"_id","id_usuario_vendedor","id_pelucia","id_usuario_recebedor","tipo_transacao"};
        String selection = "id_usuario_vendedor = ?";
        String[] selectionArgs = {String.valueOf(idUsuario)};
        Cursor data = db.query(TABLE_TRANSACAO, columns, selection, selectionArgs, null, null, null);
        while (data.moveToNext()) {
            int idColumnIndex = data.getColumnIndex("id_pelucia");
            listPeluciaId.add(Integer.parseInt(data.getString(idColumnIndex)));
        }
        db.close();
    }
}
