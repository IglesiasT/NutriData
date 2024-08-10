from fastapi import FastAPI

app = FastAPI()

@app.get("/")
def read_root():
    return {"message": "Data Analysis Service"}

@app.post("/analyze/")
def analyze_data(data: dict):
    return {"analysis": "Data Analysis Result"}


if __name__ == '__main__':
    print('hello world')
