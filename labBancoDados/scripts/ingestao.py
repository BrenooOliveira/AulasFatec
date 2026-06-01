import polars as pl

from supabase import create_client


df = pl.read_excel("/home/breno-oliveira/Documentos/gitRepositories/AulasFatec/labBancoDados/influx_modelado.xlsx",
              sheet_name="leitura_sensores",
              read_options={"header_row": 2})


df_trsuted = (
    df.rename(
    {
     "lse_sen_id (FK)": "lse_sen_id"
     }
    )
    .drop("lse_id (PK)")
    .sort("lse_timestamp", descending=False)
)

url = "https://goxsrtiefqaxvdhvihhx.supabase.co"
key = "sb_publishable_5RClX9StI8h58wgsQcyxVg_LaUySh9H"

supabase = create_client(url, key)
supabase.table("leitura_sensores").insert(df_trsuted.to_dicts()).execute()
print(supabase)
