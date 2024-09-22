import os
import json

def combine_json_files(folder_path, combined_file_name):
    combined_data = []
    output_file = os.path.join(folder_path)

    # Check if the output file already exists
    if os.path.exists(output_file):
        print(f"The output file '{output_file}' already exists. Please delete it before running the script.")
        return

    # Iterate through all files in the given folder
    for filename in os.listdir(folder_path):
        if filename.endswith('.json') and filename != combined_file_name:
            file_path = os.path.join(folder_path, filename)
            with open(file_path, 'r') as f:
                try:
                    data = json.load(f)
                    combined_data.append(data)
                except json.JSONDecodeError:
                    print(f"Error decoding JSON from file: {filename}")

    # Write the combined data to a new JSON file
    with open(output_file, 'w') as f:
        json.dump(combined_data, f)

    print(f"Combined JSON file created at: {output_file}")

combined_file_name = 'combined.json'
combine_json_files('./DumpedDataForAnalysis', combined_file_name)